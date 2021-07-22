package com.jaidev.videofy.network

data class BaseResponse<T>(val status: Int, val message: String?, val body: T?)

data class SuccessResponse(val status: Int, val message: String?)