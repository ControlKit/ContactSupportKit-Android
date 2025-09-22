package com.sepanta.controlkit.contactsupportkit.service

import com.sepanta.controlkit.contactsupportkit.service.apierror.NetworkResult
import com.sepanta.controlkit.contactsupportkit.service.apierror.handleApi
import com.sepanta.controlkit.contactsupportkit.service.model.ApiCheckUpdateResponse


class ContactSupportApi(private val apiService: ApiService) {

    suspend fun submitData(
        route: String, appId: String, version: String, deviceId: String,
        sdkVersion: String,
        email: String,
        subject: String,
        message: String
    ): NetworkResult<ApiCheckUpdateResponse> {
        return handleApi {
            apiService.getData(
                route,
                appId,
                version,
                deviceId,
                sdkVersion,
                email = email,
                subject = subject,
                message = message
            )
        }
    }
}
