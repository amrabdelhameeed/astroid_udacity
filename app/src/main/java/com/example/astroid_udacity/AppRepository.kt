package com.example.astroid_udacity
import android.util.Log
import androidx.lifecycle.LiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
class AppRepository (private val database: AsteroidDB){
    val asteroidsOfToday: LiveData<List<Asteroid>>
        get() = database.daoForAsteroid().getTodayAsteroid(SimpleDateFormat("yyyy-MM-dd").format(Date()))
    val pictureOfDay: LiveData<PictureOfDay>
        get() = database.pictureDao().getAllPics()
    val weeklyListOfAsteroid: LiveData<List<Asteroid>>
        get() = database.daoForAsteroid().getAll()
    suspend fun pictureofDay() {
        withContext(Dispatchers.IO) {
            try {
                val response = ApiServiceImpl.retrofitService.getPicture(
                    Constants.API_KEY
                ).also {
                    Log.i("tasd",it)
                }
                val emptyPic = PictureOfDay(-1, "image", "", "https://apod.nasa.gov/apod/image/2211/Lunar-Eclipse-South-Pole_1024.jpg")
                val moshi: Moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<PictureOfDay> = moshi.adapter<PictureOfDay>(PictureOfDay::class.java)
                val pic2 = jsonAdapter.fromJson(response) as PictureOfDay
                val pic = Moshi.Builder()
                    .build()
                    .adapter(PictureOfDay::class.java)
                     .fromJson(response)
                    ?:emptyPic
                database.pictureDao().updatePicture(pic2)
                Log.e("tagg", "done pic")
            } catch (e: Exception) {
                val emptyPic = PictureOfDay(-1, "image", "", "https://apod.nasa.gov/apod/image/2211/Lunar-Eclipse-South-Pole_1024.jpg")
                database.pictureDao().updatePicture(emptyPic)
                Log.e("taggs", "failed pic")
            }
        }
    }
    suspend fun reGetAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroid = ApiServiceImpl.retrofitService.getAllAsteroids()
                val myJson = JSONObject(asteroid)
                val jsonData = parseAsteroidsJsonResult(myJson)
                database.daoForAsteroid().updateData(jsonData)
                Log.e("tag", "done ast")
            } catch (e: Exception) {
                Log.e("tag2", "failed ast")
            }
        }
    }
}