package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkersViewModel : ViewModel() {

    private val _workers = MutableStateFlow<List<Worker>>(emptyList())
    val workers: StateFlow<List<Worker>> = _workers

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getByProject(projectId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getWorkers(projectId)
                _workers.value = response
            } catch (e: Exception) {
                _errorMessage.value = "$e"
            } finally {
                _isLoading.value = false
            }
        }
    }
}