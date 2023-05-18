package com.example.spectrum_themoviedb_test.ui.homepage.state

data class PaginationState(
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val totalPages: Int = 1,
)