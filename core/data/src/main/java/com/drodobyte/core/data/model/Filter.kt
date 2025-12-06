package com.drodobyte.core.data.model

enum class Filter {
    All, Lost, Found;

    val isAll get() = this == All
    val isLost get() = this == Lost
    val isFound get() = this == Found
    val incLost get() = isLost || isAll
    val incFound get() = isFound || isAll
}
