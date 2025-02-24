package com.touhidapps.room.di


import com.touhidapps.room.db.AppDatabase
import com.touhidapps.room.repository.TransactionRepository
import com.touhidapps.room.viewModel.TransactionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule: Module = module {

    // Provide MyTransactionDao
    single { get<AppDatabase>().transDao() }

    single { TransactionRepository(get()) }

    viewModel { TransactionViewModel(get()) }

}

