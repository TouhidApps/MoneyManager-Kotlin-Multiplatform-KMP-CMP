package com.touhidapps.room.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel // lifecycle-viewmodel
import org.koin.compose.currentKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.viewmodel.defaultExtras
import org.koin.viewmodel.resolveViewModel


//@Composable
//inline fun <reified T : ViewModel> koinViewModel(vararg parameters: Any): T {
//    val scope = currentKoinScope()
//    return viewModel<T> {
//        scope.get<T> {
//            parametersOf(*parameters)
//        }
//    }
//}


