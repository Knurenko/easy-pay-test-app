package presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.knurenko.easypaytestapp.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

/**
 * @author Knurenko Bogdan 28.11.2023
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupKoinFragmentFactory()

        setContentView(R.layout.activity_main)
    }
}