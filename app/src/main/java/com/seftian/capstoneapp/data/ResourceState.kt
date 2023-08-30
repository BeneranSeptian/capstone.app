package com.seftian.capstoneapp.data

sealed class ResourceState<out T: Any>{
    data class Success<out T : Any>(val data: T) : ResourceState<T>()
    data class Error(val message: String?) : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
}