package com.groundzero.qapital.base

/**
 * Both of these function use cases could be implemented synchronously.
 * Just wanted to show you use of rxJava Subject.
 */
interface MainActivityCallback {
    fun changeToolbarTitle(title: String)
    fun changeProgressBarVisibility(isVisible: Boolean)
}