package com.sg.challengechap2.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appDataStore by preferencesDataStore(
    name = "Challenge Chap 2"
)
