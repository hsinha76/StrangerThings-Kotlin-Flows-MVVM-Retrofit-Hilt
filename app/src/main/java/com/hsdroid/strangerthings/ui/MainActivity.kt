package com.hsdroid.strangerthings.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hsdroid.strangerthings.R
import com.hsdroid.strangerthings.databinding.ActivityMainBinding
import com.hsdroid.strangerthings.ui.adapter.RecyclerAdapter
import com.hsdroid.strangerthings.ui.viewmodel.QuotesViewmodel
import com.hsdroid.strangerthings.utils.ApiState
import com.hsdroid.strangerthings.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewmodel: QuotesViewmodel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerAdapter = RecyclerAdapter()

        Glide.with(this).asGif().load(R.raw.loader).into(binding.imgLoading);

        viewmodel.getQuotes()
        getData()

        binding.btnRetry.setOnClickListener {
            retryData()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getData() {
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel._response.collect {
                    when (it) {
                        is ApiState.SUCCESS -> recyclerAdapter.setList(it.data)
                            .also { initRecyclerView() }
                            .also { hideLoading() }
                        is ApiState.FAILURE -> showRetry(it.msg)
                        else -> ApiState.EMPTY
                    }
                }
            }
        }
    }

    private suspend fun hideLoading() {
        delay(3000)
        Utils.hideView(binding.imgLoading)
        Utils.showView(binding.recyclerView)
    }

    private fun showRetry(msg: Throwable) {
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()

        Utils.showView(binding.btnRetry)
        Utils.hideView(binding.imgLoading)
        Utils.hideView(binding.recyclerView)
    }

    private fun retryData() {
        viewmodel.getQuotes()
        getData()

        Utils.hideView(binding.btnRetry)
        Utils.showView(binding.imgLoading)
    }

}