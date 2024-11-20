package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.services.RetrofitClient
import com.probuildx.constructechapp.entities.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProjectsViewModel : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getByUser(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                //delay(500)
                val response = RetrofitClient.projectsService.getByUser(userId)
                _projects.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun getById(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                val response = RetrofitClient.projectsService.getById(id)
                _project.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun create(project: Project) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                RetrofitClient.projectsService.create(project)
                //getAll() // Refresh posts after creation
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun delete(projectId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                RetrofitClient.projectsService.delete(projectId)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

}