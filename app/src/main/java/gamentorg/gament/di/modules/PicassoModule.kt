package gamentorg.gament.di.modules

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [ClientModule::class])
class PicassoModule {

    @Singleton
    @Provides
    fun picasso(application: Application, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(application.applicationContext)
            .downloader(downloader)
            .build()
    }

    @Singleton
    @Provides
    fun downloader(client: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(client)
    }
}