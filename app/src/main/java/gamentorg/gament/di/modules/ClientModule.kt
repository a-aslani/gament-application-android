package gamentorg.gament.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class ClientModule {

    @Singleton
    @Provides
    fun client(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().cache(cache).build()
    }

    @Singleton
    @Provides
    fun cache(file: File): Cache {
        return Cache(file,10 * 1024 * 1024)
    }

    @Singleton
    @Provides
    fun file(application: Application): File = File(application.cacheDir, "OkHttpClientCache")
}