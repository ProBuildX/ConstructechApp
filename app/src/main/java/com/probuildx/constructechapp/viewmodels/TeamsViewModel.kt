package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.services.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamsViewModel: ViewModel() {
    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams

    private val _team = MutableStateFlow<Team?>(null)
    val team: StateFlow<Team?> = _team

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getByProject(projectId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                //delay(500)
                val response = RetrofitClient.teamsService.getByProject(projectId)
                _teams.value = response
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
                val response = RetrofitClient.teamsService.getById(id)
                _team.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun create(team: Team) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                RetrofitClient.teamsService.create(team)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun delete(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                RetrofitClient.teamsService.delete(id)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }
    
}