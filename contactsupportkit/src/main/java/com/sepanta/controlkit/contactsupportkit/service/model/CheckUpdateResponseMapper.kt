package com.sepanta.controlkit.contactsupportkit.service.model

import java.util.Locale

data class ApiCheckUpdateResponse(
    val data: ApiData
)

data class ApiData(
    val id: String?,
    val email: String?,
    val subject: String?,
    val message: String?,
    val sdk_version: Int?,
    val version: Int?,
    val created_at: String?
)


fun ApiCheckUpdateResponse.toDomain(): ContactSupportResponse {
    val d = this.data
    return ContactSupportResponse(
        id = d.id,
        version = d.version,
        sdkVersion = d.sdk_version,
        email = d.email,
        message = d.message,
        subject = d.subject,
        createdAt = d.created_at
    )
}

