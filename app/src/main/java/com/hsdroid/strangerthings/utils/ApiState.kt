package com.hsdroid.strangerthings.utils

import com.hsdroid.strangerthings.network.model.Quotes

sealed class ApiState {
    class SUCCESS(val data: List<Quotes>) : ApiState()
    class FAILURE(val msg: Throwable) : ApiState()
    object LOADING : ApiState()
    object EMPTY : ApiState()
}
