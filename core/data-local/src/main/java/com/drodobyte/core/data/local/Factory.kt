package com.drodobyte.core.data.local

import android.content.Context

internal object Factory {

    fun dataSource(context: Context) =
        ImageLocalDataSource(Store(context))
}
