package com.esperassignment.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esperassignment.local.converter.OptionConverter
import com.esperassignment.local.dao.DBDao
import com.esperassignment.local.entity.MExclusion
import com.esperassignment.local.entity.MFeature

@Database(entities = [MFeature::class, MExclusion::class], version = 1, exportSchema = false)
@TypeConverters(
   OptionConverter::class
)
abstract class FeatureDatabase : RoomDatabase() {

    abstract fun dbDao(): DBDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FeatureDatabase? = null


        fun getDatabase(context: Context): FeatureDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeatureDatabase::class.java,
                    "db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}