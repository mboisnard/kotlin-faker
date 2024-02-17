package io.github.serpro69.kfaker.commerce

import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.kotest.core.spec.style.DescribeSpec

class CommerceFakerIT : DescribeSpec({
    `every public function in each provider is invoked without exceptions`(CommerceFaker())

    `faker instance is initialized with custom locale` { faker { } }
})
