package com.sepanta.controlkit.contactsupportkit.view.config

import androidx.compose.runtime.Composable
import com.sepanta.controlkit.contactsupportkit.service.model.ContactSupportResponse
import com.sepanta.controlkit.contactsupportkit.view.viewModel.ContactSupportViewModel

interface ContactSupportContract {
    @Composable
    fun ShowView(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    )
}