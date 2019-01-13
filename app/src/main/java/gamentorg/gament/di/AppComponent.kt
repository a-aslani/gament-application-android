package gamentorg.gament.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import gamentorg.gament.BaseApplication
import gamentorg.gament.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    AppModule::class,
    ViewModelModule::class,
    PicassoModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseApplication: BaseApplication)
}