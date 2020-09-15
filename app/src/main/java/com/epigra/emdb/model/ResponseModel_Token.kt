package com.epigra.emdb.model

import com.epigra.emdb.model.base.BaseResponseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseModel_Token : Serializable{

    @SerializedName("status_code")
    var status_code: Int = 0

    @SerializedName("status_message")
    var status_message: String = ""

    @SerializedName("success")
    var success: Boolean = false

    @SerializedName("request_token")
    var request_token: String? = null
}