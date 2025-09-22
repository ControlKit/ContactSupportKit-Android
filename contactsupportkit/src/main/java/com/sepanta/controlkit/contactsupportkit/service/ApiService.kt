package com.sepanta.controlkit.contactsupportkit.service

import com.sepanta.controlkit.contactsupportkit.service.model.ApiCheckUpdateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @FormUrlEncoded
    @POST()
    suspend fun getData(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-device-uuid") deviceId: String?,
        @Header("x-sdk-version") sdkVersion: String?,
        @Field("message") message: String,
        @Field("email") email: String,
        @Field("subject") subject: String,
    ): Response<ApiCheckUpdateResponse>
}