package com.paysera.currencyexchange.ui.controller

import com.bluelinelabs.conductor.Controller

import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.paysera.currencyexchange.ui.custome_view.MainView


class MainController : Controller() {
    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @NonNull container: ViewGroup,
        @Nullable savedViewState: Bundle?
    ): View {

        return MainView(container.context)
    }
}