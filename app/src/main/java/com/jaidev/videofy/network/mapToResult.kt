package com.jaidev.videofy.network

inline fun <reified T> BaseResponse<T>.mapToResult(): T {
    return if (status == 0 && T::class == Unit::class) {
        @Suppress("UNCHECKED_CAST")
        Unit as T
    } else if (status != 200) {
        throw Exception(message ?: "")
    } else body ?: throw Exception("Something went wrong.")
}

inline fun SuccessResponse.mapToResult(): String {
    return if (status == 0) {
        @Suppress("UNCHECKED_CAST")
        Unit as String
    } else message?:""
}