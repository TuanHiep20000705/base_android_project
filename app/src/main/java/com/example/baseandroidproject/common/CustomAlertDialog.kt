package com.example.baseandroidproject.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class CustomAlertDialog : AlertDialog {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?,
    ) : super(context, cancelable, cancelListener) {
    }

    class Builder : AlertDialog.Builder {
        private var message: CharSequence? = null
        private var title: CharSequence? = null
        private var positiveButtonText: CharSequence? = null
        private var negativeButtonText: CharSequence? = null
        private var positiveListener: DialogInterface.OnClickListener? = null
        private var negativeListener: DialogInterface.OnClickListener? = null
        private val dialogText: CharSequence? get() = if (message == null) (if (title == null) "" else title) else message

        constructor(context: Context) : super(context) {}

        constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

        override fun setCancelable(cancelable: Boolean): Builder {
            super.setCancelable(cancelable)
            return this
        }

        override fun setTitle(titleId: Int): Builder {
            return this.setTitle(context.getString(titleId))
        }

        override fun setTitle(title: CharSequence): Builder {
            this.title = title
            super.setTitle(title)
            return this
        }

        override fun setPositiveButton(
            textId: Int,
            listener: DialogInterface.OnClickListener?,
        ): Builder {
            return this.setPositiveButton(context.getString(textId), listener)
        }

        override fun setPositiveButton(
            text: CharSequence,
            listener: DialogInterface.OnClickListener?,
        ): Builder {
            positiveListener = listener
            positiveButtonText = text
            super.setPositiveButton(text) { dialog: DialogInterface?, which: Int ->
                positiveListener?.let {
                    it.onClick(dialog, which)
                }
            }
            return this
        }

        override fun setNegativeButton(
            textId: Int,
            listener: DialogInterface.OnClickListener?,
        ): Builder {
            return this.setNegativeButton(context.getString(textId), listener)
        }

        override fun setNegativeButton(
            text: CharSequence,
            listener: DialogInterface.OnClickListener?,
        ): Builder {
            negativeButtonText = text
            negativeListener = listener
            super.setNegativeButton(text) { dialog: DialogInterface?, which: Int ->
                negativeListener?.let {
                    it.onClick(dialog, which)
                }
            }
            return this
        }

        override fun setMessage(messageId: Int): Builder {
            message = context.getString(messageId)
            super.setMessage(messageId)
            return this
        }

        override fun setMessage(message: CharSequence?): Builder {
            this.message = message
            super.setMessage(message)
            return this
        }

        override fun create(): AlertDialog {
            val dialog = super.create()
            return dialog
        }
    }
}