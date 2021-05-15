package com.esperassignment.model

data class MFeature(
    val feature_id: Int,
    val name: String,
    val options: List<MOption>
)
