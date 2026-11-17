package com.example.travelexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DestinationAdapter(
    private val favorites: FavoritesStore,
    private val onOpen: (Destination) -> Unit,
    private val onFavoriteChanged: () -> Unit
) : RecyclerView.Adapter<DestinationAdapter.DestinationHolder>() {

    private var items: List<Destination> = emptyList()

    fun submitList(newItems: List<Destination>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_destination, parent, false)
        return DestinationHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationHolder, position: Int) {
        val destination = items[position]
        holder.name.text = destination.name
        holder.country.text = destination.country
        holder.description.text = destination.description
        holder.category.text = destination.category.uppercase()
        holder.favorite.text = if (favorites.contains(destination.id)) "♥" else "♡"
        Glide.with(holder.image).load(destination.imageUrl)
            .centerCrop().placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery).into(holder.image)
        holder.itemView.setOnClickListener { onOpen(destination) }
        holder.favorite.setOnClickListener {
            favorites.toggle(destination.id)
            notifyItemChanged(holder.bindingAdapterPosition)
            onFavoriteChanged()
        }
    }

    override fun getItemCount() = items.size

    class DestinationHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.destinationImage)
        val name: TextView = view.findViewById(R.id.destinationName)
        val country: TextView = view.findViewById(R.id.destinationCountry)
        val description: TextView = view.findViewById(R.id.destinationDescription)
        val category: TextView = view.findViewById(R.id.categoryBadge)
        val favorite: TextView = view.findViewById(R.id.favoriteButton)
    }
}
