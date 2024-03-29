package gamentorg.gament.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gamentorg.gament.ui.game.GameFragment
import gamentorg.gament.ui.game.RuleFragment
import gamentorg.gament.ui.game.TournamentFragment
import gamentorg.gament.ui.main.MainFragment
import gamentorg.gament.ui.login.LoginPageOneFragment
import gamentorg.gament.ui.login.LoginPageTwoFragment
import gamentorg.gament.ui.login.RegisterFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoginPageOneFragment(): LoginPageOneFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoginPageTwoFragment(): LoginPageTwoFragment

    @ContributesAndroidInjector
    internal abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeGameFragment(): GameFragment

    @ContributesAndroidInjector
    internal abstract fun contributeTournamentFragment(): TournamentFragment

    @ContributesAndroidInjector
    internal abstract fun contributeRuleFragment(): RuleFragment
}