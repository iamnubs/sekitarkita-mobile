package com.linkensky.ornet.data.services

import com.linkensky.ornet.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SekitarKitaService {
    @GET("hospitals")
    suspend fun getHospitals(): BaseCollectionResponse<List<Hospital>>

    @GET("call-centers")
    suspend fun getCallCenters(): BaseCollectionResponse<List<CallCenter>>

    @GET("indonesia-statistics")
    suspend fun getIndonesia(): List<Country>

    @GET("province-statistics")
    suspend fun getProvinces(): List<Province>

    @POST("store-selfcheck")
    suspend fun storeSelfCheck(@Body request: RequestReportData): BaseResponse

    @POST("device-history")
    suspend fun deviceHistories(@Body request: InteractionHistoryRequest): InteractionHistoryResponse
}

