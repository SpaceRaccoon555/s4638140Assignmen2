
package com.example.s4638140assignmen2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.s4638140assignmen2.data.LoginRequest
import com.example.s4638140assignmen2.data.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText =
            findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText =
            findViewById<EditText>(R.id.passwordEditText)
        val loginButton =
            findViewById<Button>(R.id.loginButton)
        val loginProgressBar =
            findViewById<ProgressBar>(R.id.loginProgressBar)
        val statusText =
            findViewById<TextView>(R.id.statusText)

        loginProgressBar.visibility = android.view.View.GONE

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                statusText.text = "Please enter your student ID and password."
                return@setOnClickListener
            }

            loginButton.isEnabled = false
            loginProgressBar.visibility = android.view.View.VISIBLE
            statusText.text = ""

            lifecycleScope.launch {
                try {
                    val response = apiService.login(
                        LoginRequest(
                            username = username,
                            password = password
                        )
                    )

                    if (response.isSuccessful) {
                        val keypass = response.body()?.keypass

                        if (!keypass.isNullOrBlank()) {
                            getSharedPreferences(
                                "assignment_settings",
                                MODE_PRIVATE
                            ).edit()
                                .putString("keypass", keypass)
                                .apply()

                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            statusText.text =
                                "Login failed: the server returned no keypass."
                        }
                    } else {
                        statusText.text =
                            "Login failed. Check your student ID and password."
                    }
                } catch (e: Exception) {
                    statusText.text =
                        "Could not connect. Check your internet and try again."
                } finally {
                    loginButton.isEnabled = true
                    loginProgressBar.visibility = android.view.View.GONE
                }
            }
        }
    }
}