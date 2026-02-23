package com.examen.plume

import android.app.Application
import com.examen.plume.data.database.AppDatabase
import com.examen.plume.data.repository.CitationRepository

class PlumeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { CitationRepository(database.citationDao()) }
}
