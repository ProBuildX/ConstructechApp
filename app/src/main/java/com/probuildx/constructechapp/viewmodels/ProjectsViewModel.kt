package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.services.RetrofitClient
import com.probuildx.constructechapp.entities.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProjectsViewModel : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getAll()
                _projects.value = response
            } catch (e: Exception) {
                _errorMessage.value = "$e"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun get(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.get(id)
                _project.value = response
            } catch (e: Exception) {
                _errorMessage.value = "$e"
            }
        }
    }

    fun create(project: Project) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.create(project)
                getAll() // Refresh posts after creation
            } catch (e: Exception) {
                _errorMessage.value = "$e"
            } finally {
                _isLoading.value = false
            }
        }
    }

}