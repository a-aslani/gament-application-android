package gamentorg.gament.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gamentorg.gament.ui.GameActivity
import gamentorg.gament.ui.LoginActivity
import gamentorg.gament.ui.MainActivity
import gamentorg.gament.ui.SplashActivity

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