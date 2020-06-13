package com.linkensky.ornet.data.model

import java.math.BigInteger

data class ChangeStatusRequest(
    val device_id: String,
    val health: String,
    val phone: String,
    val name: String,
    val travelHistory: String? = null,
    val nik: String? = null
)

data class ReportDataRequest(
    val device_id: String,
    val has_fever: Boolean,
    val has_flu: Boolean,
    val has_cough: Boolean,
    val has_breath_problem: Boolean,
    val has_sore_throat: Boolean,
    val has_in_infected_country: Boolean,
    val has_in_infected_city: Boolean,
    val has_direct_contact: Boolean,
    val result: String,
    val name: String,
    val phone: String,
    val address: String,
    val age: String
)

data class FirebaseTokenRequest(
    val device_id: String,
    val firebase_token: String
)

data class InteractionHistoryRequest(
    val device_id: String
)

data class StoreDeviceRequest(
    val device_id: String,
    val nearby_device: String,
    val latitude: Double?,
    val longitude: Double?,
    val speed: Float?,
    val device_name: String?
)

data class StoreLocationRequest(
    val device_id: String,
    val latitude: Double,
    val longitude: Double,
    val speed: Float? = 0f,
    val area: String?,
    val address: String?
)

data class RequestDataSIKM(
    val device_id: String,
    val nik: String,
    val name: String,
    val phone: String,
    val originable: BigInteger? = null,
    val destinable: BigInteger? = null,
    val category: String = "Sekali Jalan",
    val ktp_file: String,
    val medical_file: String,
    val medical_issued: String
)

data class StoreSIKMRequest(
    val data: List<RequestDataSIKM>
)