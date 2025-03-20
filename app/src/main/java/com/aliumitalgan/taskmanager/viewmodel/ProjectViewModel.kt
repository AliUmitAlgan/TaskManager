package com.aliumitalgan.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumitalgan.taskmanager.model.Project
import com.aliumitalgan.taskmanager.repository.ProjectRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _projects.value = repository.getProjects().first()  // 👈 **Burada first() ekledik**
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProjectById(projectId: String?): Flow<Project?> {
        return projects.map { projectList ->
            projectList.find { it.id == projectId }
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch {
            repository.addProject(project)
            loadProjects()
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            repository.updateProject(project)
            loadProjects()
        }
    }
}
