package com.otb.githubtracker.common.base

import com.otb.githubtracker.common.LoadingSpinner

/**
 * Created by Mohit Rajput on 13/08/22.
 */
interface DisplaysLoadingSpinner {
    val loadingSpinner: LoadingSpinner

    fun showLoadingSpinner() {
        loadingSpinner.show()
    }

    fun dismissLoadingSpinner() {
        loadingSpinner.dismiss()
    }
}