package com.ccooy.gankio.model

/**
 * Category 返回model
 */

data class CategoryResult(
        var error: Boolean,
        var results: List<ResultsBean> = emptyList()
)

data class ResultsBean(
        var _id: String,
        var createdAt: String,
        var desc: String,
        var publishedAt: String,
        var source: String,
        var type: String,
        var url: String,
        var used: Boolean,
        var who: String,
        var images: List<String> = emptyList()
)

