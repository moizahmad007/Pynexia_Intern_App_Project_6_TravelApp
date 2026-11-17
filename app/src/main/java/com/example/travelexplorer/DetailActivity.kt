package com.example.travelexplorer

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var destination: Destination
    private lateinit var favorites: FavoritesStore
    private lateinit var favoriteButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        favorites = FavoritesStore(this)
        val id = intent.getStringExtra("destination_id")
        destination = DestinationRepository.destinations.firstOrNull { it.id == id }
            ?: run { finish(); return }

        val image = findViewById<ImageView>(R.id.detailImage)
        favoriteButton = findViewById(R.id.detailFavorite)
        Glide.with(this).load(destination.imageUrl).centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery).into(image)

        findViewById<TextView>(R.id.detailName).text = destination.name
        findViewById<TextView>(R.id.detailCountry).text = destination.country
        findViewById<TextView>(R.id.detailCategory).text = destination.category.uppercase()
        findViewById<TextView>(R.id.detailDescription).text = destination.description
        findViewById<TextView>(R.id.travelTip).text = destination.tip
        updateFavorite()
        findViewById<TextView>(R.id.backButton).setOnClickListener { finish() }
        favoriteButton.setOnClickListener {
            favorites.toggle(destination.id)
            updateFavorite()
        }
        loadLiveDescription()
    }

    private fun updateFavorite() {
        favoriteButton.text = if (favorites.contains(destination.id)) "♥" else "♡"
    }

    private fun loadLiveDescription() {
        val progress = findViewById<ProgressBar>(R.id.detailProgress)
        progress.visibility = View.VISIBLE
        ApiClient.service.getSummary(destination.wikiTitle.replace(" ", "_"))
            .enqueue(object : Callback<WikiResponse> {
                override fun onResponse(call: Call<WikiResponse>, response: Response<WikiResponse>) {
                    progress.visibility = View.GONE
                    val summary = response.body()?.extract
                    if (response.isSuccessful && !summary.isNullOrBlank()) {
                        findViewById<TextView>(R.id.detailDescription).text = summary
                        response.body()?.thumbnail?.source?.let { url ->
                            Glide.with(this@DetailActivity).load(url).centerCrop().into(findViewById(R.id.detailImage))
                        }
                    }
                }
                override fun onFailure(call: Call<WikiResponse>, t: Throwable) {
                    progress.visibility = View.GONE
                    // Keep the bundled description available when offline.
                }
            })
    }
}
