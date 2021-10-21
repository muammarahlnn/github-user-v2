package com.ardnn.githubuserv2.utils

import android.view.View
import android.widget.ProgressBar

object Helper {
    fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}