package com.epigra.emdb.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseModel_Genres : Serializable {

    @SerializedName("genres")
    var genresArray: ArrayList<Genre>? = null

    inner class Genre : Serializable {
        @SerializedName("id")
        var id: Int? = null

        @SerializedName("name")
        var name: String? = null
    }
}