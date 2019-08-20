package com.groundzero.qapital.data.response

class Response<T>(
    val status: Status,
    val listData: List<T>?,
    val throwable: Throwable?
) {
    companion object{
        fun <T> loading(): Response<T> {
            return Response(Status.LOADING, null, null)
        }

        fun <T> success(data: List<T>): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): Response<T> {
            return Response(Status.ERROR, null, throwable)
        }
    }
}