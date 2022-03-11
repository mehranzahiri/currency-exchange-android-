package com.paysera.currencyexchange

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.paysera.currencyexchange.databinding.ActivityApplicationBinding

class ApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_application)

        handlePaymentIntent(intent)
    }

    private fun handlePaymentIntent(intent: Intent?) {
        // Todo : handle incoming intent
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handlePaymentIntent(intent)
    }

}