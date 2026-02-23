package com.examen.plume.data.database

import android.content.Context
import androidx.room.*
import com.examen.plume.data.converter.CategoryConverter
import com.examen.plume.data.dao.CitationDao
import com.examen.plume.data.entity.Citation

@Database(
    entities = [Citation::class],
    version = 3, // J'ai incrémenté la version
    exportSchema = false
)
@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun citationDao(): CitationDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citation_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}