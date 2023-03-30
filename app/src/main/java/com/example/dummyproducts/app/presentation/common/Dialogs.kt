package com.example.dummyproducts.app.presentation.common

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.dummyproducts.databinding.DialogWaitBinding

fun Context.createWaitAlertDialog(title: String, message: String) {
    return with(AlertDialog.Builder(this)) {
        val binding = DialogWaitBinding.inflate(LayoutInflater.from(this@createWaitAlertDialog))
        binding.textViewMessage.text = message

        setTitle(title)
        setView(binding.root)
        setCancelable(false)


        create()
    }
}