package com.android.abc.activity

import androidx.appcompat.widget.Toolbar


interface SetupActionBar {
    fun setup(toolbar: Toolbar, fragmentId: Int)
}