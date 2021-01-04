package me.alfredobejarano.bitsocodechallenge.model

import java.lang.Exception

sealed class Result<out T : Any> {
    object Progress : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

