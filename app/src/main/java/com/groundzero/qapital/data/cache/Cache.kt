package com.groundzero.qapital.data.cache

interface Cache<T> {
    fun getCachedData(): T?
    fun cacheData(t: T)
}