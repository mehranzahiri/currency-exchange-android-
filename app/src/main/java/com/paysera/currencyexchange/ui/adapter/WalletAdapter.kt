package com.paysera.currencyexchange.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamekara.app.ui.adapter.DefaultDiffAdapter
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.ItemWalletBinding
import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import com.paysera.currencyexchange.viewModel.RateViewModel

class WalletAdapter(
    val paymentViewModel: RateViewModel
) : DefaultDiffAdapter<WalletAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TransactionViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_wallet,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(
            items[position] as WalletStruct
        )
    }

    inner class TransactionViewHolder(private val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionStruct: WalletStruct) {
            binding.also {
                binding.item = transactionStruct
                binding.data = paymentViewModel

                binding.executePendingBindings()
            }
        }
    }
}