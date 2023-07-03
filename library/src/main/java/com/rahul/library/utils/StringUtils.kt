package com.rahul.library.utils

import android.content.Context
import androidx.annotation.StringRes
import java.text.MessageFormat

fun Context.getArgumentedText(@StringRes res: Int, vararg placeholders: String) =
    MessageFormat.format(getString(res), *placeholders)
