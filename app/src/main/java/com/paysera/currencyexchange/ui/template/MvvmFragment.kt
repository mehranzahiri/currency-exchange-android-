package com.paysera.currencyexchange.ui.template

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paysera.currencyexchange.BR
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.notificationcenter.FireCenter
import com.paysera.currencyexchange.viewModel.BaseViewModel

abstract class MvvmFragment<T : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes private val layoutRes: Int,
    private val viewModelClazz: Class<V>
) : FlowFragment() {

    protected lateinit var binding: T
    protected lateinit var data: V

    private var isFirstCreation: Boolean = true

    private lateinit var progressDialog: ProgressDialog

    // set this property for toolbar
    val toolbarTitle = MutableLiveData<String>()

    @MainThread
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            layoutRes,
            container,
            false
        ) ?: return null

        return binding.root
    }

    @MainThread
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = ViewModelProvider(requireActivity()).get(viewModelClazz).apply { onCreate() }

        isFirstCreation = data.isFirstCreation

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.data, data)
        }

        progressDialog = ProgressDialog(requireContext()).apply {
            setMessage(getString(R.string.loading))
            setCancelable(false)
        }

        data.goTo.observe(viewLifecycleOwner, {
            it?.let { it1 -> findNavController().navigate(it1) }
        })

        data.goToDir.observe(viewLifecycleOwner, {
            it?.let { it1 -> findNavController().navigate(it1) }
        })

        data.goToByBundle.observe(viewLifecycleOwner, {
            it?.let { findNavController().navigate(it.first, it.second) }
        })

        data.gotToActivity.observe(this, {
            activity?.startActivity(Intent(activity, it))
        })

        data.gotToActivityWithIntent.observe(this, {
            requireActivity().setResult(AppCompatActivity.RESULT_OK, it)
            requireActivity().finish()
        })

        data.hideKeyboard.observe(viewLifecycleOwner, {
            hideKeyboard()
        })

        data.showMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it?.let { it1 -> getString(it1) }, Toast.LENGTH_SHORT)
                .show()
        })

        data.showMessageStr.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        data.showProgressDialog.observe(viewLifecycleOwner, {
            if (it == true) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        FireCenter.coroutineException.observe(viewLifecycleOwner, {
            data.showProgressDialog.value = false
        })

        onEveryInitialization(savedInstanceState)

        if (isFirstCreation) onFirstInitialization()

    }


    @MainThread
    protected open fun onEveryInitialization(savedBundle: Bundle?) {
        // Nothing to do here.
    }

    @MainThread
    protected open fun onFirstInitialization() {
        // Nothing to do here.
    }

}