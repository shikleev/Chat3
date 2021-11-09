package com.example.chat.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed class ViewState<out T> {
    data class Success<T>(val data: T, val paging: Paging = Paging.NONE) : ViewState<T>()

    data class Error<T>(
        val isShowError: Boolean = true,
        val error: Exception? = null,
    ) : ViewState<T>()

    data class Progress<T>(val isShowProgress: Boolean = true) : ViewState<T>()

    inline fun onSuccess(block: (T) -> Unit) {
        if (this is Success) block(this.data)
    }

    inline fun onSuccess(block: (T, Paging) -> Unit) {
        if (this is Success) block(this.data, this.paging)
    }

    inline fun onProgress(block: (isShowProgress: Boolean) -> Unit) {
        if (this is Progress) block(this.isShowProgress)
    }

    inline fun onError(block: (isShowError: Boolean, error: Exception?) -> Unit) {
        if (this is Error) block(this.isShowError, this.error)
    }

    enum class Paging {
        NONE, START, END
    }
}

fun <T> Flow<ViewState<T>>.onEachViewState(block: ViewState<T>.() -> Unit): Flow<ViewState<T>> {
    onEach { block(it) }
    return this
}

fun <T> LiveData<ViewState<T>>.observeViewState(viewLifecycleOwner: LifecycleOwner, block: ViewState<T>.() -> Unit) {
    observe(viewLifecycleOwner, Observer {
        block(it)
    })
}

fun <T> LiveData<ViewState<T>>.observeViewStateOnce(lifecycleOwner: LifecycleOwner, block: ViewState<T>.() -> Unit) {
    observe(lifecycleOwner, object : Observer<ViewState<T>> {
        override fun onChanged(t: ViewState<T>) {
            block(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
    if (this is MutableLiveData) {
        return this
    } else {
        error("This LiveData is not mutable")
    }
}

fun <T> MutableLiveData<ViewState<T>>.setSuccess(value: T, paging: ViewState.Paging = ViewState.Paging.NONE) {
    this.value = ViewState.Success(value, paging)
}

fun <T> MutableLiveData<ViewState<T>>.setProgress(isShowProgress: Boolean = true) {
    this.value = ViewState.Progress(isShowProgress)
}

fun <T> MutableLiveData<ViewState<T>>.setError(isShowError: Boolean = true, error: Exception? = null) {
    this.value = ViewState.Error(isShowError, error)
}

fun <T> MutableLiveData<ViewState<T>>.postSuccess(value: T, paging: ViewState.Paging = ViewState.Paging.NONE) {
    this.postValue(ViewState.Success(value, paging))
}

fun <T> MutableLiveData<ViewState<T>>.postProgress(isShowProgress: Boolean = true) {
    this.postValue(ViewState.Progress(isShowProgress))
}

fun <T> MutableLiveData<ViewState<T>>.postError(isShowError: Boolean = true, error: Exception? = null) {
    this.postValue(ViewState.Error(isShowError, error))
}
