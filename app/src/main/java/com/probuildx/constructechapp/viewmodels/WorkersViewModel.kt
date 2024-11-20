package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.services.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkersViewModel : ViewModel() {

    private val _workers = MutableStateFlow<List<Worker>>(emptyList())
    val workers: StateFlow<List<Worker>> = _workers

    private val _worker = MutableStateFlow<Worker?>(null)
    val worker: StateFlow<Worker?> = _worker

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getByProject(projectId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(500)
                val response = RetrofitClient.workersService.getByProject(projectId)
                _workers.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun getById(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(500)
            try {
                val response = RetrofitClient.workersService.getById(id)
                _worker.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun create(worker: Worker) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(500)
            try {
                RetrofitClient.workersService.create(worker)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun delete(workerId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(500)
            try {
                RetrofitClient.workersService.delete(workerId)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun getByTeam(teamId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(500)
                val response = RetrofitClient.workersService.getByTeam(teamId)
                _workers.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }
}