package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.entities.Material
import com.probuildx.constructechapp.services.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MaterialsViewModel: ViewModel() {
    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials: StateFlow<List<Material>> = _materials

    private val _material = MutableStateFlow<Material?>(null)
    val material: StateFlow<Material?> = _material

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getByProject(projectId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(500)
                val response = RetrofitClient.materialsService.getByProject(projectId)
                _materials.value = response
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
                val response = RetrofitClient.materialsService.getById(id)
                _material.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun create(material: Material) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(500)
            try {
                RetrofitClient.materialsService.create(material)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun delete(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(500)
            try {
                RetrofitClient.materialsService.delete(id)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }
}