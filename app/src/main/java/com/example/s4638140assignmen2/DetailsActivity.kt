
package com.example.s4638140assignmen2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val titleText = findViewById<TextView>(R.id.detailsTitle)
        val contentText = findViewById<TextView>(R.id.detailsContent)

        val entityJson = intent.getStringExtra("entity_json")

        if (entityJson.isNullOrBlank()) {
            titleText.text = "Entity Details"
            contentText.text = "No entity details were provided."
            return
        }

        try {
            val entity = com.google.gson.JsonParser
                .parseString(entityJson)
                .asJsonObject

            val properties = entity.entrySet()

            titleText.text = properties.firstOrNull()
                ?.let { it.value.asString }
                ?: "Entity Details"

            contentText.text = properties.joinToString("\n\n") { entry ->
                val value = if (entry.value.isJsonNull) {
                    "Not available"
                } else {
                    entry.value.toString().removeSurrounding("\"")
                }

                "${entry.key}\n$value"
            }
        } catch (e: Exception) {
            titleText.text = "Entity Details"
            contentText.text = "Unable to display the entity details."
        }
    }
}
