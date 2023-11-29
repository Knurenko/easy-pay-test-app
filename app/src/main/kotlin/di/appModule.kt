package di

import data.mappers.PaymentResponseToModelMapper
import data.network.EasyPayService
import data.network.di.RetrofitServiceFactory
import data.repo.AuthRepoImpl
import data.repo.PaymentRepoImpl
import domain.repo.AuthRepository
import domain.repo.PaymentRepository
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.ui.screens.auth.AuthFragment
import presentation.ui.screens.auth.AuthScreenViewModel

/**
 * @author Knurenko Bogdan 29.11.2023
 */

val appModule = module {
    single<EasyPayService> { RetrofitServiceFactory.create() }
    single<AuthRepository> { AuthRepoImpl(service = get()) }
    single<PaymentRepository> {
        PaymentRepoImpl(
            service = get(),
            mapper = PaymentResponseToModelMapper()
        )
    }

    viewModel { AuthScreenViewModel(authRepo = get()) }
    fragment { AuthFragment(authScreenViewModel = get()) }
}