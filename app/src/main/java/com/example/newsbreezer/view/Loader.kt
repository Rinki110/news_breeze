package com.example.newsbreezer.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.newsbreezer.R


class Loader {

    companion object{

        private var dialog: Dialog? = null

        fun show(context: Context, title: String = "Please wait...") {

            Handler(context.mainLooper).post {
                dialog = Dialog(context)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setCancelable(false)
                dialog!!.setContentView(R.layout.dialog_loader)

                val vw_text = dialog!!.findViewById<View>(R.id.vw_text) as TextView
                vw_text.text = title

                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                dialog!!.show()
                dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }

        fun hide() {
            if (dialog != null) {
                Handler(dialog!!.context.mainLooper).post {
                    dialog!!.dismiss()
                }
            }
        }
    }
    

}
