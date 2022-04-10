package com.taufiq.movies.data.remote.model

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("results")
    val results: T? = null,
    @SerializedName("page")
    val page: Int? = null,
)