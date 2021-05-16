package com.esperassignment.model

data class MDB(
    val features: List<MFeature>,
    val exclusions: List<List<MExclusion>>
)
