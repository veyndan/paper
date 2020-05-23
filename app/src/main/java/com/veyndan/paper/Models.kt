package com.veyndan.paper

data class Item(
        val profile: String,
        val username: String,
        val name: String,
        val date: Long,
        val image: String,
        val text: String,
        val width: Int,
        val height: Int
)

enum class SortOrder {
    ASCENDING, DESCENDING
}

// TODO Would be cool to make this into DSL
data class Query(
        val filterTexts: List<FilterText> = emptyList(),
        val filterUsernames: List<FilterUsername> = listOf(
                FilterUsername(username = "champagnepapi"),
                FilterUsername(username = "offsetyrn"),
                FilterUsername(username = "ovo40"),
                FilterUsername(username = "partynextdoor"),
                FilterUsername(username = "quavohuncho"),
                FilterUsername(username = "yrntakeoff")),
        val sort: Sort = Sort(date = SortOrder.DESCENDING)
)

data class FilterText(val text: String)

data class FilterUsername(val username: String)

data class Sort(val date: SortOrder)