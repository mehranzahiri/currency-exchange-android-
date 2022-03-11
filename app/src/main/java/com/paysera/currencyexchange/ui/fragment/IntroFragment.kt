package com.paysera.currencyexchange.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.paysera.currencyexchange.ui.template.MvvmFragment
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.FragmentIntroBinding
import com.paysera.currencyexchange.viewModel.AuthViewModel

class IntroFragment : MvvmFragment<FragmentIntroBinding, AuthViewModel>(
    R.layout.fragment_intro,
    AuthViewModel::class.java
) {
    private val startDelay = 3000L

    override fun onEveryInitialization(savedBundle: Bundle?) {

        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.action_splashFragment_to_bottom_fragment)
        }, startDelay)
    }
}
