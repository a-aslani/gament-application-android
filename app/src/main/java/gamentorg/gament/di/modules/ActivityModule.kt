package gamentorg.gament.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gamentorg.gament.GameActivity
import gamentorg.gament.LoginActivity
import gamentorg.gament.MainActivity
import gamentorg.gament.SplashActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun contributeGameActivity(): GameActivity
}