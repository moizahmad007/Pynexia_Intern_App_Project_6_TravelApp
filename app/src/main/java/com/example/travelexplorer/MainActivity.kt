package com.example.travelexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DestinationAdapter
    private lateinit var favorites: FavoritesStore
    private lateinit var search: EditText
    private lateinit var categoryGroup: ChipGroup
    private lateinit var countText: TextView
    private var selectedCategory = "All"
    private var showingFavorites = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favorites = FavoritesStore(this)
        search = findViewById(R.id.searchInput)
        categoryGroup = findViewById(R.id.categoryGroup)
        countText = findViewById(R.id.countText)

        val recycler = findViewById<RecyclerView>(R.id.destinationRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = DestinationAdapter(favorites, { destination ->
            startActivity(Intent(this, DetailActivity::class.java).putExtra("destination_id", destination.id))
        }, { renderList() })
        recycler.adapter = adapter

        listOf("All", "Mountains", "Beach", "City", "History").forEach { category ->
            val chip = Chip(this).apply {
                text = category
                isCheckable = true
                isClickable = true
                setTextColor(resources.getColorStateList(
                    com.google.android.material.R.color.mtrl_chip_text_color, theme))
                setOnClickListener {
                    selectedCategory = category
                    showingFavorites = false
                    findViewById<TextView>(R.id.listTitle).text = "Popular destinations"
                    renderList()
                }
            }
            categoryGroup.addView(chip)
            if (category == "All") chip.isChecked = true
        }

        search.addTextChangedListener(SimpleTextWatcher { renderList() })
        findViewById<TextView>(R.id.homeTab).setOnClickListener {
            showingFavorites = false
            findViewById<TextView>(R.id.listTitle).text = "Popular destinations"
            renderList()
        }
        findViewById<TextView>(R.id.favoritesTab).setOnClickListener {
            showingFavorites = true
            findViewById<TextView>(R.id.listTitle).text = "Your saved places"
            renderList()
        }
        renderList()
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) renderList()
    }

    private fun renderList() {
        val query = search.text?.toString()?.trim().orEmpty()
        val result = DestinationRepository.destinations.filter { destination ->
            val categoryMatches = selectedCategory == "All" || destination.category == selectedCategory
            val favoriteMatches = !showingFavorites || favorites.contains(destination.id)
            val queryMatches = destination.name.contains(query, true) ||
                destination.country.contains(query, true) || destination.category.contains(query, true)
            categoryMatches && favoriteMatches && queryMatches
        }
        adapter.submitList(result)
        countText.text = "${result.size} places"
    }
}
