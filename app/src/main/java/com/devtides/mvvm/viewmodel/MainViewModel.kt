package com.devtides.mvvm.viewmodel

import androidx.lifecycle.*
import com.devtides.mvvm.model.DataItem
import com.devtides.mvvm.model.DataItemProvider
import com.devtides.mvvm.util.SafeMutableLiveData
import com.devtides.mvvm.util.SaveableMutableSaveStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val itemLD = MutableLiveData<List<DataItem>>()

    private val _stateFlow =
        SaveableMutableSaveStateFlow(savedStateHandle, "FlowKey", 0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _liveDataPost =
        SafeMutableLiveData(savedStateHandle, "LivePostKey", 0)
    val liveDataPost: LiveData<Int> = _liveDataPost

    private val _liveDataSetWithContext =
        SafeMutableLiveData(savedStateHandle, "LiveSetWithContextKey", 0)
    val liveDataSetWithContext: LiveData<Int> = _liveDataSetWithContext

    private val _liveDataSetLaunch =
        SafeMutableLiveData(savedStateHandle, "LiveSetLaunchKey", 0)
    val liveDataSetLaunch: LiveData<Int> = _liveDataSetLaunch


    fun getItems() {
        val items = DataItemProvider.getDataItems(Random.nextInt(100))
        itemLD.value = items
    }

    // Summary Image
    // https://medium.com/mobile-app-development-publication/explore-livedata-stateflow-value-emitting-behavior-3f5c9e8ef2e3
    // https://miro.medium.com/max/1600/1*obYw-NStxxSXddgvjXaFpQ.png

    fun triggerStateFlow() {
        viewModelScope.launch(Dispatchers.Default) {
            repeat(10) {
                delay(500)
                ++_stateFlow.value
            }
        }
        Thread.sleep(1500)
    }

    fun triggerLivePostData() {
        viewModelScope.launch(Dispatchers.Default) {
            repeat(10) {
                delay(500)
                _liveDataPost.postValue(_liveDataPost.value + 1)
            }
        }
        Thread.sleep(1500)
    }

    fun triggerLiveSetWithContextData() {
        viewModelScope.launch(Dispatchers.Default) {
            repeat(10) {
                delay(500)
                withContext(Dispatchers.Main) {
                    ++_liveDataSetWithContext.value
                }
            }
        }
        Thread.sleep(1500)
    }

    fun triggerLiveSetLaunch() {
        viewModelScope.launch(Dispatchers.Default) {
            repeat(10) {
                delay(500)
                launch(Dispatchers.Main) {
                    ++_liveDataSetLaunch.value
                }
            }
        }
        Thread.sleep(1500)
    }
}