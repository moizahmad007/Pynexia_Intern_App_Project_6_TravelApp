package com.example.travelexplorer

data class Destination(
    val id: String,
    val name: String,
    val country: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val wikiTitle: String,
    val tip: String
)
