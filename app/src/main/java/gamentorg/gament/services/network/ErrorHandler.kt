package gamentorg.gament.services.network

import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor() {

    fun badRequest(errorBody: String) : String {
        return try {
            val jo = JSONObject(errorBody)
            val error = JSONObject(jo.getString("data"))
            error.getString("error")
        }catch (e: Exception) {
            ""
        }

    }
}