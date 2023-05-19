package com.hsdroid.strangerthings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsdroid.strangerthings.ui.repository.QuotesRepository
import com.hsdroid.strangerthings.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewmodel @Inject constructor(private val repository: QuotesRepository) : ViewModel() {
    private val response: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.EMPTY)

    val _response: StateFlow<ApiState> = response

    fun getQuotes() = viewModelScope.launch {
        response.value = ApiState.LOADING

        repository.getQuotes()
            .onStart {
                response.value = ApiState.LOADING
            }
            .catch {
                response.value = ApiState.FAILURE(it)
            }
            .collect {
                response.value = ApiState.SUCCESS(it)
            }
    }

}