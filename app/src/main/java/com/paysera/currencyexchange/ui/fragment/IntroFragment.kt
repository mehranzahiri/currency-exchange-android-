package com.paysera.currencyexchange.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.paysera.currencyexchange.ui.template.MvvmFragment
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.FragmentIntroBinding
import com.paysera.currencyexchange.viewModel.AuthViewModel

class IntroFragment : MvvmFragment<FragmentIntroBinding, AuthViewModel>(
    R.layout.fragment_intro,
    AuthViewModel::class.java
) {

    override fun onEveryInitialization(savedBundle: Bundle?) {
        data.initIntroFragment()

    }
}
