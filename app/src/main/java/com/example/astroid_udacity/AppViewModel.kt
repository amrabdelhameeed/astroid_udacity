package com.example.astroid_udacity
import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDB.getInstance(application)
    private val repository = AppRepository(database)
    val pictureOfDay = repository.pictureOfDay
    private val weeklyListOfAsteroid = repository.weeklyListOfAsteroid
    private val asteroidsOfToday = repository.asteroidsOfToday
    private val _navigation = MutableLiveData<Asteroid?>()
    val asteroids: MediatorLiveData<List<Asteroid>> = MediatorLiveData()
    val navigation: LiveData<Asteroid?>
        get() = _navigation
    init {
        getAsteroidData()
    }
    private fun getAsteroidData() {
        viewModelScope.launch {
            repository.reGetAsteroids()
            repository.pictureofDay()
            asteroids.addSource(weeklyListOfAsteroid) {
                asteroids.value = it
            }
        }
    }
    fun displayAsteroid(asteroid: Asteroid) {
        _navigation.value = asteroid
    }
    
    fun clearDisplayedAsteroid() {
        _navigation.value = null
    }
    fun todayView() {
        removeSource()
        asteroids.addSource(asteroidsOfToday) {
            asteroids.value = it
        }
    }
    fun weekView() {
        removeSource()
        asteroids.addSource(weeklyListOfAsteroid) {
            asteroids.value = it
        }
    }
    fun onSavedAsteroidsClicked() {
        removeSource()
        asteroids.addSource(weeklyListOfAsteroid) {
            asteroids.value = it
        }
    }

    private fun removeSource() {
        asteroids.removeSource(asteroidsOfToday)
        asteroids.removeSource(weeklyListOfAsteroid)
    }
}