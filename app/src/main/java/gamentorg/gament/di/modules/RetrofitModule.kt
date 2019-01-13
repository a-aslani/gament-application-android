package gamentorg.gament.di.modules

import dagger.Module
import dagger.Provides
import gamentorg.gament.constants.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ClientModule::class])
class RetrofitModule {

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${Config.SERVER_ADDRESS}/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}