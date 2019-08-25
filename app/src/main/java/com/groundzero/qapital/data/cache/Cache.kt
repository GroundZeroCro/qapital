package com.groundzero.qapital.data.cache

interface Cache<T> {
    fun cacheData(t: T)
    fun getCachedData(id: Int?): T?
}