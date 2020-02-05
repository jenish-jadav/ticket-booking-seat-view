package jj.app.mobii.world.helpers

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import java.io.IOException

object Messages {
    fun toast(context: Context, message: String) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun log(message: String) {
        Log.d("Jenish", message)
    }

    fun onFailureMessageRetrofit(throwable: Throwable): String {
        return if (throwable is IOException) {
            TEXT_NO_INTERNET_CONNECTION
        } else {
            TEXT_SERVER_ERROR
        }
    }

    var TEXT_SERVER_ERROR = "Server Error."
    var TEXT_NO_INTERNET_CONNECTION = "No internet connection."
}