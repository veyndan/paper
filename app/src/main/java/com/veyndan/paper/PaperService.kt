package com.veyndan.paper

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PaperService {

    @GET("{query}")
    fun items(@Path("query") query: String): Single<List<Item>>
}
