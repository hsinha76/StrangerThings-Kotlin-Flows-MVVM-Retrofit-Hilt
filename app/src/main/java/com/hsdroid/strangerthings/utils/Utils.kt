package com.hsdroid.strangerthings.utils

import android.view.View

class Utils {
    companion object {
        fun hideView(view: View) {
            view.visibility = View.GONE
        }

        fun showView(view: View) {
            view.visibility = View.VISIBLE
        }
    }
}