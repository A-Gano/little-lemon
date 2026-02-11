package com.example.littlelemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlin.text.category

data class HomeUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val filteredMenuItems: List<MenuItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMenuItems()
    }

    fun loadMenuItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val isEmpty = withContext(Dispatchers.IO) {
                    menuRepository.isDatabaseEmpty()
                }

                if (isEmpty) {
                    refreshMenu()
                } else {
                    menuRepository.getMenuItems().collect { menuItems ->
                        val categories = menuItems
                            .map { it.category }
                            .distinct()
                            .sorted()

                        _uiState.update {
                            it.copy(
                                menuItems = menuItems,
                                filteredMenuItems = applyFilters(menuItems, it.searchQuery, it.selectedCategory),
                                categories = categories,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        error = "Failed to load menu: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun refreshMenu() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val result = menuRepository.refreshMenu()
                result.onSuccess { menuItems ->
                    val categories = menuItems
                        .map { it.category }
                        .distinct()
                        .sorted()

                    _uiState.update {
                        it.copy(
                            menuItems = menuItems,
                            filteredMenuItems = applyFilters(menuItems, it.searchQuery, it.selectedCategory),
                            categories = categories,
                            isLoading = false
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = "Failed to refresh: ${error.message}",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        error = "Failed to refresh: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredMenuItems = applyFilters(currentState.menuItems, query, currentState.selectedCategory)
            )
        }
    }

    fun selectCategory(category: String?) {
        _uiState.update { currentState ->
            val newCategory = if (currentState.selectedCategory == category) null else category
            currentState.copy(
                selectedCategory = newCategory,
                filteredMenuItems = applyFilters(currentState.menuItems, currentState.searchQuery, newCategory)
            )
        }
    }

    private fun applyFilters(
        menuItems: List<MenuItem>,
        searchQuery: String,
        selectedCategory: String?
    ): List<MenuItem> {
        var filtered = menuItems

        if (selectedCategory != null) {
            filtered = filtered.filter { it.category == selectedCategory }
        }

        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true)
            }
        }

        return filtered
    }

    fun clearFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = "",
                selectedCategory = null,
                filteredMenuItems = currentState.menuItems
            )
        }
    }
}