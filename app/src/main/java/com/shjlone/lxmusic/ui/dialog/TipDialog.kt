package com.shjlone.lxmusic.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

/**
 * 提示弹窗
 *
 * @author: a564
 * @Date 2021/5/26
 */
class TipDialog {

    companion object {
        /**
         * 提示弹窗
         *
         * @param title 标题
         * @param msg 提示信息
         */
        fun createDialog(context: Context, title: String, msg: String) {
            val builder: AlertDialog.Builder? = AlertDialog.Builder(context)
            builder?.setMessage(msg)?.setTitle(title)
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }

        /**
         * 确认弹窗
         * @param title 标题
         * @param msg   提示内容
         * @param okCallback 确认回调
         * @param cancelCallback 取消回调
         *
         */
        fun createConfirmDialog(
            context: Context,
            title: String,
            msg: String,
            okCallback: (() -> Unit)? = null,
            cancelCallback: (() -> Unit)? = null
        ) {
            val builder: AlertDialog.Builder? = AlertDialog.Builder(context)
            builder?.apply {
                setPositiveButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        okCallback?.invoke()
                    })
                setNegativeButton(android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        cancelCallback?.invoke()
                    })
            }
            builder?.setMessage(msg)?.setTitle(title)
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()


        }
    }
}