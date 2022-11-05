package com.example.astroid_udacity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDB : RoomDatabase(){
    abstract fun daoForAsteroid(): DaoForAsteroid
    abstract fun pictureDao(): PictureDao
        companion object {
            private var INSTANCE: AsteroidDB ? = null
            fun getInstance(context: Context): AsteroidDB {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AsteroidDB::class.java,
                            "asteroid_database"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return INSTANCE!!
            }
        }
    }