package com.rahul.library.managers

import android.content.Context
import android.content.Intent
import com.rahul.library.BuildConfig
import com.rahul.library.callbacks.AppUpdateCallback
import com.rahul.library.network.Response
import com.rahul.library.network.Service
import com.rahul.library.network.UpdateType
import com.rahul.library.ui.flexible.FlexibleUpdateDialog
import com.rahul.library.ui.immediate.UpdateAvailableActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class InAppUpdate {
    private lateinit var apiKey: String
    private lateinit var callback: AppUpdateCallback
    private lateinit var context: Context
    private var versionCode by Delegates.notNull<Int>()

    private var service: Service = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build()
        )
        .baseUrl(BuildConfig.API)
        .build()
        .create(Service::class.java)

    fun setApiKey(context: Context, apiKey: String): InAppUpdate {
        this.apiKey = apiKey
        this.context = context
        versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        NotificationManager.getInstance().init(context)
        return this
    }

    fun listen(callback: AppUpdateCallback) {
        this.callback = callback
        getLatestUpdate()
    }

    private fun getLatestUpdate() {
        if (!this::apiKey.isInitialized) {
            throw Exception("You must set Api key with InAppUpdate.getInstance().setApiKey() before listen()")
        }
        service.getLatestVersion(apiKey).enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                val data = response.body()
                checkForUpdates(data)
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                throw t
            }
        })
    }

    private fun checkForUpdates(data: Response?) {
        data?.let {
            if (it.isUpdateAvailable()) {
                installUpdate(it)
            }
        }
        callback.onFinishCheckForUpdate()
    }

    private fun installUpdate(data: Response) {
        when (data.type) {
            UpdateType.IMMEDIATE -> {
                context.startActivity(Intent(context, UpdateAvailableActivity::class.java).apply {
                    putExtra(APP, data)
                })
            }

            UpdateType.FLEXIBLE -> {
                FlexibleUpdateDialog(context, data)
            }
        }

    }

    private fun Response.isUpdateAvailable() = code > versionCode

    companion object {
        private lateinit var instance: InAppUpdate
        fun getInstance() =
            if (this::instance.isInitialized) {
                instance
            } else {
                instance = InAppUpdate()
                instance
            }

        const val APP = "android.rahul.app.model"
    }
}
