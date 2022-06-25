package com.sdk.planetnew.data.util.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.log(e: String) {
    Log.d("TAG", e)
}

fun ViewModel.log(e: String) {
    Log.d("ViewModelTAG", e)
}

fun Fragment.snack(v: View, text: String) {
    Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
}