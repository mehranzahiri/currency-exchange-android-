package com.paysera.currencyexchange

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.paysera.currencyexchange.databinding.ActivityApplicationBinding
import com.paysera.currencyexchange.ui.controller.MainController

class ApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicationBinding
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_application)

        handlePaymentIntent(intent)

        ///////////////////////////////////////////////////////////////////////////

        router = Conductor.attachRouter(this, binding.controllerContainer, savedInstanceState)
            .setPopRootControllerMode(Router.PopRootControllerMode.NEVER)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }

    private fun handlePaymentIntent(intent: Intent?) {
        // Todo : handle incoming intent
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handlePaymentIntent(intent)
    }

}