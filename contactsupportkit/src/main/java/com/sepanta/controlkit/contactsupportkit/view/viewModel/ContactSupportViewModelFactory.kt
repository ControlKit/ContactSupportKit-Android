package com.sepanta.controlkit.contactsupportkit.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sepanta.controlkit.contactsupportkit.service.ContactSupportApi
import kotlin.jvm.java

class ContactSupportViewModelFactory(
    private val api: ContactSupportApi,

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactSupportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactSupportViewModel(api) as T
        }
        throw kotlin.IllegalArgumentException("Unknown ViewModel class")
    }
}