package com.example.domain.model

data class Page(
    val page: Int = 1,
    val limit: Int = 10,
    private var isLastPage: Boolean = false,
) {
    fun setIsLastPage(lastResponseValuesCount: Int) {
        isLastPage = lastResponseValuesCount < limit
    }

    fun isLastPage() = isLastPage
}
