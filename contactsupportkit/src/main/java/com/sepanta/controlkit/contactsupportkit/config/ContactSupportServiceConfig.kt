package com.sepanta.controlkit.contactsupportkit.config

import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportViewConfig


data class ContactSupportServiceConfig(
    var viewConfig: ContactSupportViewConfig = ContactSupportViewConfig(),
    var version: String = "1.0.0",
    var appId: String ,
    var deviceId: String?=null,
    var timeOut: Long = 5000L,
    var timeRetryThreadSleep: Long = 1000L,
    var maxRetry: Int = 5,
    )