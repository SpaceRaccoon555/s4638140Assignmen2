
package com.example.s4638140assignmen2.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s4638140assignmen2.R
import com.google.gson.JsonObject

class EntityAdapter(
    private val entities: List<JsonObject>,
    private val onEntityClick: (JsonObject) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    class EntityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.entityTitle)
        val summary: TextView = view.findViewById(R.id.entitySummary)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val entity = entities[position]
        val properties = entity.entrySet()
            .filter { it.key != "description" }

        holder.title.text = properties.firstOrNull()?.key
            ?.let { key -> entity.get(key)?.asString ?: "Entity" }
            ?: "Entity ${position + 1}"

        holder.summary.text = properties
            .drop(1)
            .joinToString(" • ") { entry ->
                "${entry.key}: ${entry.value}"
            }
            .ifBlank { "Tap to view details" }

        holder.itemView.setOnClickListener {
            onEntityClick(entity)
        }
    }

    override fun getItemCount(): Int = entities.size
}
