package com.epigra.emdb.model.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponseModel<T: Any> : Serializable {
    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_results")
    var total_results: Int = 0

    @SerializedName("total_pages")
    var total_pages: Int = 0

    @SerializedName("results")
    var results: T? = null
}