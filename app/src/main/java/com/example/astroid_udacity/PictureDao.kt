package com.example.astroid_udacity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PictureDao {
        @Transaction
        fun updatePicture(pic: PictureOfDay): Long {
            deleteAllPics()
            return insertPic(pic)
        }
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertPic(pic: PictureOfDay): Long
        @Query("DELETE FROM picture_table")
        fun deleteAllPics()
        @Query("SELECT * FROM picture_table")
        fun getAllPics(): LiveData<PictureOfDay>
}