package com.sg.challengechap2.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sg.challengechap2.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow


val Context.appDataStore by preferencesDataStore(
    name = "sgChallengeBinar"
)