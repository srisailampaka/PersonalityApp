package com.personalitytestapp.utils
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import com.personalitytestapp.R

/*
Class for common fuctionalitys
 */
class CommonUtil {
    companion object {
        // method for check the network connection
        fun isNetworkStatusAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            connectivityManager?.let {
                it.activeNetworkInfo?.let {
                    if (it.isConnected) return true
                }
            }
            return false
        }

        // method for show the alert dialog
        fun showAlertDialog(context: Context, title: String, message: String, onClickListener: DialogInterface.OnClickListener) {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton("OK", onClickListener)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


    }

}