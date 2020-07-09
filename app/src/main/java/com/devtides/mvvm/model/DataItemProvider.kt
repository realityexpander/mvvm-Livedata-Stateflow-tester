package com.devtides.mvvm.model

object DataItemProvider {
    fun getDataItems(size: Int) =
        (0..size).toList().map { DataItem(it) }
}