package com.epigra.emdb.model

import com.epigra.emdb.model.base.BaseResponseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseModel_Movies : BaseResponseModel<ArrayList<ResponseModel_Movies.MovieModel>>() {

    inner class MovieModel : Serializable {
        @SerializedName("popularity")
        var popularity: Double? = null

        @SerializedName("vote_count")
        var vote_count: Int? = null

        @SerializedName("video")
        var video: Boolean? = null

        @SerializedName("poster_path")
        var poster_path: String? = null

        @SerializedName("id")
        var id: Int? = null

        @SerializedName("adult")
        var adult: Boolean? = null

        @SerializedName("backdrop_path")
        var backdrop_path: String? = null

        @SerializedName("original_language")
        var original_language: String? = null

        @SerializedName("original_title")
        var original_title: String? = null

        @SerializedName("genre_ids")
        var genre_ids: ArrayList<Int>? = null

        @SerializedName("title")
        var title: String? = null

        @SerializedName("vote_average")
        var vote_average: Double? = null

        @SerializedName("overview")
        var overview: String? = null

        @SerializedName("release_date")
        var release_date: String? = null
    }

}