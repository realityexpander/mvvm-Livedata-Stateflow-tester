package com.devtides.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.mvvm.model.DataItem
import com.devtides.mvvm.model.DataItemProvider
import kotlin.random.Random

class MainViewModel: ViewModel() {

    val itemLD = MutableLiveData<List<DataItem>>()

    fun getItems() {
        val items = DataItemProvider.getDataItems(Random.nextInt(100))
        itemLD.value = items
    }
}