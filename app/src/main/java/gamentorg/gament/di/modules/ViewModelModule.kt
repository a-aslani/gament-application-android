package gamentorg.gament.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import gamentorg.gament.vm.*

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(productViewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TournamentViewModel::class)
    abstract fun bindTournamentViewModel(tournamentViewModel: TournamentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RuleViewModel::class)
    abstract fun bindRuleViewModel(ruleViewModel: RuleViewModel): ViewModel
}