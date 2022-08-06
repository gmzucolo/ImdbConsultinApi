package com.example.gmzucolo.imdbconsultingapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmzucolo.imdbconsultingapi.model.Movie
import com.example.gmzucolo.imdbconsultingapi.model.NetworkResult
import com.example.gmzucolo.imdbconsultingapi.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var _movieResponse = MutableLiveData<NetworkResult<List<Movie>>>()
    val movieResponse: LiveData<NetworkResult<List<Movie>>> = _movieResponse

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            mainRepository.getPopularMovies().collect {
                _movieResponse.postValue(it)
            }
        }
    }
}