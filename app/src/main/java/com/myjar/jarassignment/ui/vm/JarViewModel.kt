package com.myjar.jarassignment.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val _navigateToItem = MutableStateFlow<String?>(null)
    val navigateToItem: StateFlow<String?>
        get() = _navigateToItem

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch {
            val flowData=repository.fetchResults()

            flowData.collect{
                _listStringData.value=it
            }
            flow {
                emit(_listStringData.value)
            }
        }
    }

    fun navigateToItemDetail(id: String) {
        viewModelScope.launch {
            _navigateToItem.emit(id)
        }
    }
}