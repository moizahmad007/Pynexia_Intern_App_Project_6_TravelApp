package com.example.travelexplorer

import android.content.Context

class FavoritesStore(context: Context) {
    private val prefs = context.getSharedPreferences("travel_favorites", Context.MODE_PRIVATE)
    fun contains(id: String) = prefs.getStringSet("ids", emptySet())?.contains(id) == true
    fun toggle(id: String): Boolean {
        val ids = prefs.getStringSet("ids", emptySet())?.toMutableSet() ?: mutableSetOf()
        val added = if (ids.contains(id)) { ids.remove(id); false } else { ids.add(id); true }
        prefs.edit().putStringSet("ids", ids).apply()
        return added
    }
    fun all(): Set<String> = prefs.getStringSet("ids", emptySet())?.toSet() ?: emptySet()
}
