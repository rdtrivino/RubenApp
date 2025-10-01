package com.rubentrivino.rubenapp.model

import android.content.Context
import org.json.JSONArray
import org.json.JSONException

class TaskStore(context: Context) {

    private val prefs = context.getSharedPreferences("tasks_prefs", Context.MODE_PRIVATE)
    private val KEY = "tasks_json"

    fun load(): MutableList<String> {
        val json = prefs.getString(KEY, null) ?: return mutableListOf()
        return try {
            val arr = JSONArray(json)
            MutableList(arr.length()) { i -> arr.getString(i) }
        } catch (_: JSONException) {
            mutableListOf()
        }
    }

    fun save(list: List<String>) {
        val arr = JSONArray()
        list.forEach { arr.put(it) }
        prefs.edit().putString(KEY, arr.toString()).apply()
    }
}
