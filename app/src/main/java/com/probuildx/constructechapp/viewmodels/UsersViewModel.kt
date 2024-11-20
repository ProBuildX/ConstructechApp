package com.probuildx.constructechapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probuildx.constructechapp.entities.User
import com.probuildx.constructechapp.services.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel: ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getById(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                val response = RetrofitClient.usersService.getById(id)
                _user.value = response
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun getByEmail(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                val response = RetrofitClient.usersService.getByEmail(email)
                _user.value = response.firstOrNull()
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }

    fun create(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            //delay(500)
            try {
                RetrofitClient.usersService.create(user)
            }
            catch (e: Exception) { _errorMessage.value = "$e" }
            finally { _isLoading.value = false }
        }
    }
}