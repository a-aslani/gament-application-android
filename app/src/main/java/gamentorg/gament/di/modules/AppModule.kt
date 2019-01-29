package gamentorg.gament.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Typeface
import android.preference.PreferenceManager
import androidx.core.content.res.ResourcesCompat
import dagger.Module
import dagger.Provides
import gamentorg.gament.R
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.services.network.ApiService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class AppModule {

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun appDatabase(application: Application): AppDatabase = AppDatabase.appDatabase(application)

    @Singleton
    @Provides
    fun sharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    }

    @Singleton
    @Provides
    @Named("farsi")
    fun typeFaceIranYekanMedium(application: Application): Typeface {
        return ResourcesCompat.getFont(application.applicationContext, R.font.iranyekanmedium)!!
    }
}