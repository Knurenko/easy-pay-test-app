package di

import android.content.Context
import data.mappers.PaymentResponseToModelMapper
import data.network.EasyPayService
import data.network.di.RetrofitServiceFactory
import data.repo.AuthRepoImpl
import data.repo.PaymentRepoImpl
import data.storage.UserTokenStorage
import domain.repo.AuthRepository
import domain.repo.PaymentRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.ui.screens.auth.AuthFragment
import presentation.ui.screens.auth.AuthScreenViewModel
import presentation.ui.screens.payments.PaymentFragment
import presentation.ui.screens.payments.PaymentScreenViewModel

/**
 * @author Knurenko Bogdan 29.11.2023
 */

val appModule = module {
    single<UserTokenStorage> {
        UserTokenStorage(
            androidContext().getSharedPreferences(
                "Token_storage_prefs",
                Context.MODE_PRIVATE
            )
        )
    }

    single<EasyPayService> { RetrofitServiceFactory.create() }

    single<AuthRepository> { AuthRepoImpl(service = get()) }
    single<PaymentRepository> {
        PaymentRepoImpl(
            service = get(),
            mapper = PaymentResponseToModelMapper()
        )
    }

    viewModel { AuthScreenViewModel(authRepo = get()) }
    fragment { AuthFragment(viewModel = get(), userTokenStorage = get()) }

    viewModel { PaymentScreenViewModel(paymentRepository = get()) }
    fragment { PaymentFragment(viewModel = get(), userTokenStorage = get()) }
}