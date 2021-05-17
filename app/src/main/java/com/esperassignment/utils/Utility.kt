package com.esperassignment.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import com.esperassignment.R
import com.google.android.material.snackbar.Snackbar

object Utility {

    private lateinit var snackBar: Snackbar

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
//            action.setTextAppearance(R.style.snackBarActionStyle)
        }
        snackBar.show()
    }

}