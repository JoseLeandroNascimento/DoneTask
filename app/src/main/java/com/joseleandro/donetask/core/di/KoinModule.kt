package com.joseleandro.donetask.core.di

import com.joseleandro.donetask.ui.viewmodel.NavigationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {

    viewModelOf(::NavigationViewModel)

}