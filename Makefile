SHELL  := env ORCHID_DIAGNOSE=$(ORCHID_DIAGNOSE) $(SHELL)
ORCHID_DIAGNOSE ?= false
.DEFAULT_GOAL := help

# https://stackoverflow.com/a/10858332
# Check that given variables are set and all have non-empty values,
# die with an error otherwise.
#
# Params:
#   1. Variable name(s) to test.
#   2. (optional) Error message to print.
check_defined = \
    $(strip $(foreach 1,$1, \
        $(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = \
    $(if $(value $1),, \
      $(error Undefined $1$(if $2, ($2))))

# use java 17 to build because 'org.graalvm.buildtools.native' in cli-bot requires java version >= 11
# both the libs and the cli app use java toolchains and will be built with java compatibility of version 8
__java_version_ok := $(shell java -version 2>&1|grep '17.0' >/dev/null; printf $$?)

.PHONY: check_java
check_java: ## check current java version (mostly used in other targets)
ifneq ($(__java_version_ok),$(shell echo 0))
	$(error "Expected java 17")
endif

.PHONY: deploy-docs
deploy-docs: ## deploys documentation with orchid
	sed -i 's/^\s\sbaseUrl:\shttp:\/\/localhost:8080/  baseUrl: https:\/\/serpro69.github.io\/kotlin-faker/' ./docs/src/orchid/resources/config.yml
	sed -i 's/^\s\shomePageOnly:.*/#/' ./docs/src/orchid/resources/config.yml
	./gradlew :docs:orchidDeploy -PorchidEnvironment=prod -PorchidDiagnose=$(ORCHID_DIAGNOSE)
	git checkout ./docs/src/orchid/resources/config.yml

.PHONY: snapshot-in-pre-release
_snapshot-in-pre-release: ## (DEPRECATED) publishes next snapshot in current pre-release version
	./gradlew clean test integrationTest \
	printVersion \
	nativeCompile \
	publishToSonatype \
	-PpromoteToRelease \
	--info

.PHONY: snapshot-major
_snapshot-major: ## (DEPRECATED) publishes next snapshot with a major version bump
	./gradlew clean test integrationTest \
	printVersion \
	nativeCompile \
	publishToSonatype \
	-PbumpComponent=major \
	--info

.PHONY: snapshot-minor
snapshot-minor: check_java ## publishes next snapshot with a minor version bump
	@:$(call check_defined, VERSION, semantic version string - 'X.Y.Z(-rc.\d+)?')

	./gradlew clean test integrationTest -Pversion='$(VERSION)-SNAPSHOT'
	./gradlew nativeCompile -Pversion='$(VERSION)-SNAPSHOT' --info
	./gradlew publishToSonatype -Pversion='$(VERSION)-SNAPSHOT' --info

.PHONY: snapshot-patch
_snapshot-patch: ## (DEPRECATED) publishes next snapshot with a patch version bump
	./gradlew clean test integrationTest \
	printVersion \
	nativeCompile \
	publishToSonatype \
	-PbumpComponent=patch \
	--info

.PHONY: pre-release-major
_pre-release-major: ## (DEPRECATED) publishes next pre-release version with a major version bump
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=major \
	--info

	git push origin --tags

.PHONY: pre-release-minor
_pre-release-minor: ## (DEPRECATED) publishes next pre-release with a minor version bump
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=minor \
	--info

	git push origin --tags

.PHONY: pre-release-patch
_pre-release-patch: ## (DEPRECATED) publishes next pre-release with a patch version bump
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=patch \
	--info

	git push origin --tags

.PHONY: next-pre-release
_next-pre-release: ## (DEPRECATED) publishes next pre-release version
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpreRelease \
	--info

	git push origin --tags

.PHONY: promote-to-release
_promote-to-release: ## (DEPRECATED) publishes next release from the current pre-release version
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpromoteToRelease \
	--info

	git push origin --tags

.PHONY: release-major
_release-major: ## (DEPRECATED) publishes next major release version
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=major \
	--info

	git push origin --tags

.PHONY: release-minor
_release-minor: ## (DEPRECATED) publishes next minor release version
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=minor \
	--info

	git push origin --tags

.PHONY: release-patch
_release-patch: ## (DEPRECATED) publishes next patch release version
	./gradlew clean test integrationTest \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=patch \
	--info

	git push origin --tags

.PHONY: release
release: check_java ## publishes the next release with a specified VERSION
	@:$(call check_defined, VERSION, semantic version string - 'X.Y.Z(-rc.\d+)?')

	# run tests
	./gradlew clean test integrationTest -Pversion=$(VERSION)
	# build and test native image
	./gradlew nativeCompile -Pversion=$(VERSION) --info
	./cli-bot/build/native/nativeCompile/faker-bot_$(VERSION) list --verbose >/dev/null || false
	./cli-bot/build/native/nativeCompile/faker-bot_$(VERSION) lookup a --verbose >/dev/null || false
	# publish to sonatype and close staging repo
	./gradlew publishToSonatype closeSonatypeStagingRepository -Pversion=$(VERSION) --info
	# create and push git tag
	git tag v$(VERSION)
	git push origin --tags

.PHONY: help
help: ## Displays this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
