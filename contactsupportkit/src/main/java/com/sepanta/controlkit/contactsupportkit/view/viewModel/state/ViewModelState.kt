package com.sepanta.controlkit.contactsupportkit.view.viewModel.state

import com.sepanta.controlkit.contactsupportkit.service.model.ContactSupportResponse
import com.sepanta.errorhandler.ApiError


sealed class ViewModelState {
    object Initial : ViewModelState()
    object NoContent : ViewModelState()
    data class Success(val data: ContactSupportResponse?) : ViewModelState()
    data class Error(val data: ApiError<*>?) : ViewModelState()


}

