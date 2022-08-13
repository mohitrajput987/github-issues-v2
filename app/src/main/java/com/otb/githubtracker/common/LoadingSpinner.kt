package com.otb.githubtracker.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.otb.githubtracker.databinding.CustomLoadingCentreBinding


/**
 * Created by Mohit Rajput on 13/08/22.
 * Material loading spinner
 */
class LoadingSpinner(private val context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    fun show() {
        val binding = CustomLoadingCentreBinding.inflate((context as Activity).layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setLayout(-1, -1)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing && !(context as Activity).isDestroyed) {
            dialog.dismiss()
        }
    }
}


