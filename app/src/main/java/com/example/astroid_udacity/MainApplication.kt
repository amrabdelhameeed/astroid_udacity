package com.example.astroid_udacity
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            setupPeriodicWork()
        }
    }
    @SuppressLint("IdleBatteryChargingConstraints")
    private fun setupPeriodicWork() {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .build()
        val refreshRequest = PeriodicWorkRequestBuilder<AppWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "Refresh Periodically",
            ExistingPeriodicWorkPolicy.KEEP,
            refreshRequest
        )
    }


}
class AppWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: AppRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        repository.reGetAsteroids()
        Result.success()
    } catch (exception: HttpException) {
        Result.retry()
    }
}