package com.epigra.emdb.di.components

import com.epigra.emdb.di.modules.ModuleApi
import com.epigra.emdb.viewmodel.ViewModel_Main
import com.epigra.emdb.viewmodel.ViewModel_Splash
import com.gayebilisim.belsis.di.modules.ModuleApp
import com.gayebilisim.belsis.di.modules.ModuleAppDataCache
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModuleApi::class, ModuleApp::class, ModuleAppDataCache::class])
interface ComponentMovies {
    fun injectViewModel(viewModel: ViewModel_Main)
}