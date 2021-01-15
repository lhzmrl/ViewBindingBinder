package tech.lhzmrl.viewbinding.binder.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.lhzmrl.bind.viewbinding.demo.databinding.ActivityMainBinding
import tech.lhzmrl.viewbinding.binder.annotation.ViewBinding

class MainActivity : AppCompatActivity() {

    @ViewBinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_fragment, MultipleItemFragment.newInstance(3), "MultipleItemFragment")
            .commit()
    }

}