package com.example.cleanarchitecturesolution.core.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG

fun View.showSnackbar(message: String?) {
    message?.let {
        Snackbar.make(this, message, LENGTH_LONG).show()
    }
}
