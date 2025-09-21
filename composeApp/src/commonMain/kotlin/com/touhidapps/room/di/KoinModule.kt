package com.touhidapps.room.di

import com.touhidapps.room.data.datasource.local.db.AppDatabase
import com.touhidapps.room.domain.repo.TransactionRepository
import com.touhidapps.room.domain.usecase.TransactionDeleteUseCase
import com.touhidapps.room.domain.usecase.TransactionGetAllUseCase
import com.touhidapps.room.domain.usecase.TransactionUpsertUseCase
import com.touhidapps.room.presentation.common.CommonContract
import com.touhidapps.room.data.repo.TransactionRepositoryImpl
import com.touhidapps.room.presentation.common.CommonViewModel
import com.touhidapps.room.presentation.home.HomeContract
import com.touhidapps.room.presentation.home.TransactionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule: Module = module {

    // Provide MyTransactionDao
    single { get<AppDatabase>().transDao() }

    single<TransactionRepository> { TransactionRepositoryImpl(get()) }

    factory { TransactionGetAllUseCase(get()) }
    factory { TransactionUpsertUseCase(get()) }
    factory { TransactionDeleteUseCase(get()) }

    viewModel { CommonViewModel() }
    single<CommonContract> { get<CommonViewModel>() } // app-wide singleton

//    viewModel { TransactionViewModel(get(), get(), get()) }
//
//    factory<HomeContract> { get<TransactionViewModel>() } // while using no need koinViewModel<TransactionViewModel>() only use get()

    viewModel { TransactionViewModel(get(), get(), get()) } bind HomeContract::class



}

