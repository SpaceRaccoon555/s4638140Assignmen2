
package com.example.s4638140assignmen2.data

import com.google.gson.JsonObject

data class DashboardResponse(
    val entities: List<JsonObject>,
    val entityTotal: Int
)
