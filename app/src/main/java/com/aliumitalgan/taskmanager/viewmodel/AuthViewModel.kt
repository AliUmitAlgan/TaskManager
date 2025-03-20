package com.aliumitalgan.taskmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumitalgan.taskmanager.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        _isLoggedIn.value = repository.getCurrentUserId() != null
    }

    fun registerUser(name: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                repository.registerUser(name, email, password)
                _isLoggedIn.value = true
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                repository.loginUser(email, password)
                _isLoggedIn.value = true
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logoutUser() {
        repository.logoutUser()
        _isLoggedIn.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
