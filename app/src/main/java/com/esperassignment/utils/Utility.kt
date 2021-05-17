package com.esperassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import com.esperassignment.R
import com.google.android.material.snackbar.Snackbar

object Utility {

    private lateinit var snackBar: Snackbar

    // for show snackbar
    fun showSnackBar(mContext: Context, v: View?, msg: String?) {
        snackBar= Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
            .setAction(mContext.getString(R.string.ok)) { snackBar.dismiss() }
        val params = snackBar.view.layoutParams as MarginLayoutParams
        snackBar.view.layoutParams = params
        val message: TextView = snackBar.view.findViewById(R.id.snackbar_text)
        val action: TextView = snackBar.view.findViewById(R.id.snackbar_action)
        message.maxLines = 3
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            message.setTextAppearance(R.style.snackBarStyle)
            action.setTextAppearance(R.style.snackBarActionStyle)
        }
        snackBar.show()
    }


    // checking whether network is available or not

    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return false
    }

}