package com.nexogic.apiservices

import com.example.samplekotlinapplication.SpecialistsModel
import io.reactivex.Observable

import retrofit2.http.*
import kotlin.collections.ArrayList


interface ApiInterface {
    //    <---------------Authentication Apis---------------------->

    @GET("v2/list")
    fun getPics(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Observable<ArrayList<SpecialistsModel>>

}