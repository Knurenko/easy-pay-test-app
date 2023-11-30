package data.storage

import android.content.SharedPreferences

/**
 * @author Knurenko Bogdan 30.11.2023
 */
class UserTokenStorage(private val prefs: SharedPreferences) {
    var userToken: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) = prefs.edit().putString(TOKEN_KEY, value).apply()

    private companion object {
        const val TOKEN_KEY = "auth token key"
    }
}