package com.sdk.planetnew.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdk.planetnew.R
import com.sdk.planetnew.databinding.ActivityEnterBinding

class EnterActivity : BaseActivity() {
    private lateinit var binding: ActivityEnterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation(binding.appCompatTextView)
        changeWindow(window)
        intent(this)

    }
}