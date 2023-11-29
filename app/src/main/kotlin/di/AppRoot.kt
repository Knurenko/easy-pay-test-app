package di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

/**
 * @author Knurenko Bogdan 29.11.2023
 */
class AppRoot : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppRoot)
            fragmentFactory()
            modules(appModule)
        }
    }
}