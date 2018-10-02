package fud.fud

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import fud.fud.databinding.ActivityMainBinding

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainvm = MainActivityVM("Chinese", "Distance")
    }
}
