package com.example.gmzucolo.imdbconsultingapi.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.gmzucolo.imdbconsultingapi.databinding.ActivityMainBinding
import com.example.gmzucolo.imdbconsultingapi.model.Movie
import com.example.gmzucolo.imdbconsultingapi.model.NetworkResult
import com.example.gmzucolo.imdbconsultingapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovies.adapter = movieAdapter

        movieAdapter.setItemClick(object : ClickInterface<Movie> {
            override fun onClick(data: Movie) {
                Toast.makeText(this@MainActivity, data.title, Toast.LENGTH_SHORT).show()
            }
        })
        mainViewModel.movieResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                }
                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }
                is NetworkResult.Success -> {
                    movieAdapter.updateMovies(it.data)
                    binding.progressbar.isVisible = false
                }
            }
        }
    }

}