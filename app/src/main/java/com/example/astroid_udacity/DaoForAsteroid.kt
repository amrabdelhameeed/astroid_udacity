package com.example.astroid_udacity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoForAsteroid {
        @Query("SELECT * FROM asteroid_table ORDER BY date(closeApproachDate) ASC")
        fun getAll(): LiveData<List<Asteroid>>
        @Query("SELECT * FROM asteroid_table WHERE closeApproachDate <=:date ORDER BY date(closeApproachDate) ASC ")
        fun getTodayAsteroid(date: String): LiveData<List<Asteroid>>
        @Transaction
        fun updateData(users: List<Asteroid>): List<Long> {
            deleteAll()
            return insertAll(users)
        }
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(asteroids: List<Asteroid>): List<Long>
        @Query("DELETE FROM asteroid_table")
        fun deleteAll()
}