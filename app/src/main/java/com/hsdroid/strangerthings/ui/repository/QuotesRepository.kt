package com.hsdroid.strangerthings.ui.repository

import com.hsdroid.strangerthings.network.ApiInterface
import com.hsdroid.strangerthings.network.model.Quotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getQuotes(): Flow<List<Quotes>> = flow {
        emit(apiInterface.getQuotes())
    }.flowOn(Dispatchers.IO)

}