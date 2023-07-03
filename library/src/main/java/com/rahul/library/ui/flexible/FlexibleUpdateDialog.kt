package com.rahul.library.ui.flexible

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.rahul.library.R
import com.rahul.library.managers.AppUpdateCenter
import com.rahul.library.network.UpdateResponse
import com.rahul.library.services.InAppUpdateService
import com.rahul.library.utils.getArgumentedText

class FlexibleUpdateDialog(
    private val context: Context,
    private val data: UpdateResponse
) : Dialog(context) {
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_flexible_update, null, false)

    init {
        setContentView(view)
        setupViews()
        show()
    }

    private fun setupViews() {
        findViewById<TextView>(R.id.app_update_title).text =
            context.getArgumentedText(R.string.ask_update, data.app)
        findViewById<TextView>(R.id.app_info).text =
            context.getArgumentedText(R.string.app_update_info, data.app)
        findViewById<TextView>(R.id.version_info).text =
            context.getArgumentedText(R.string.version_info, data.activeRelease?.name ?: "")
        findViewById<Button>(R.id.update).setOnClickListener {
            dismiss()
            context.startService(Intent(context, InAppUpdateService::class.java).apply {
                putExtra(AppUpdateCenter.APP, this@FlexibleUpdateDialog.data)
            })
        }
        findViewById<Button>(R.id.cancel).setOnClickListener {
            dismiss()
        }
    }
}
