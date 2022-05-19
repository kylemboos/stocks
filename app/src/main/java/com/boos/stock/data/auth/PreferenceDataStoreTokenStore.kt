package com.boos.stock.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.boos.stock.domain.repository.TokenStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferenceDataStoreTokenStore @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : TokenStore {

    private val Context.dataStore by preferencesDataStore(name = "token")

    override suspend fun getToken(): String? {
        val key = stringPreferencesKey(PREF_TOKEN_KEY)
        return applicationContext.dataStore.data.map { preferences ->
            preferences[key]
        }.firstOrNull()
    }

    override suspend fun saveToken(token: String) {
        val key = stringPreferencesKey(PREF_TOKEN_KEY)
        applicationContext.dataStore.edit { preferences ->
            preferences[key] = token
        }
    }

    companion object {
        private const val PREF_TOKEN_KEY = "PREF_TOKEN_KEY"
    }
}