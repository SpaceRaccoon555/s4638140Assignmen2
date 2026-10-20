
package com.example.s4638140assignmen2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s4638140assignmen2.data.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.s4638140assignmen2.ui.dashboard.EntityAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val countText = findViewById<TextView>(R.id.entityCountText)
        val statusText = findViewById<TextView>(R.id.dashboardStatusText)
        val progressBar = findViewById<ProgressBar>(R.id.dashboardProgressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.entitiesRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val keypass = getSharedPreferences(
            "assignment_settings",
            MODE_PRIVATE
        ).getString("keypass", null)

        if (keypass.isNullOrBlank()) {
            progressBar.visibility = View.GONE
            countText.text = "No dashboard data"
            statusText.text = "Please log in again."
            return
        }

        lifecycleScope.launch {
            try {
                val response = apiService.getDashboard(keypass)

                if (response.isSuccessful) {
                    val dashboard = response.body()

                    if (dashboard != null) {
                        countText.text = "Total entities: ${dashboard.entityTotal}"
                        recyclerView.adapter = EntityAdapter(dashboard.entities) { entity ->
                            val intent = Intent(this@DashboardActivity, DetailsActivity::class.java)
                            intent.putExtra("entity_json", entity.toString())
                            startActivity(intent)
                        }
                        statusText.text = if (dashboard.entities.isEmpty()) {
                            "No entities were returned."
                        } else {
                            ""
                        }
                    } else {
                        statusText.text = "The server returned an empty response."
                    }
                } else {
                    countText.text = "Unable to load dashboard"
                    statusText.text = "Server error: ${response.code()}"
                }
            } catch (e: Exception) {
                countText.text = "Unable to load dashboard"
                statusText.text = "Could not connect. Please try again."
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
