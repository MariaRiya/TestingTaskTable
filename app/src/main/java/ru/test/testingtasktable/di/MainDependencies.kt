package ru.test.testingtasktable.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.test.testingtasktable.MainViewModel

object MainDependencies {
    val mainModule = module {
        viewModel { MainViewModel() }
    }
}