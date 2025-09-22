package com.sepanta.controlkit.contactsupportkit.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sepanta.controlkit.contactsupportkit.config.ContactSupportServiceConfig
import com.sepanta.controlkit.contactsupportkit.utils.isValidEmail
import com.sepanta.controlkit.contactsupportkit.service.ContactSupportApi
import com.sepanta.controlkit.contactsupportkit.view.viewModel.state.ViewModelState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.sepanta.controlkit.contactsupportkit.BuildConfig
import com.sepanta.controlkit.contactsupportkit.service.apierror.NetworkResult
import com.sepanta.controlkit.contactsupportkit.service.apierror.model.ErrorValidation
import com.sepanta.controlkit.contactsupportkit.service.model.toDomain
import com.sepanta.errorhandler.ApiError
import com.sepanta.errorhandler.ErrorEntityRegistry

class ContactSupportViewModel(
    private val api: ContactSupportApi,
) : ViewModel() {
    private val url = BuildConfig.API_URL

    fun setupErrorEntities() {
        ErrorEntityRegistry.register(ErrorValidation::class.java)
    }

    private var config: ContactSupportServiceConfig? = null

    fun setConfig(config: ContactSupportServiceConfig) {
        this.config = config
        setupErrorEntities()
    }

    private val _mutableState = MutableStateFlow<ViewModelState>(ViewModelState.Initial)
    val state: StateFlow<ViewModelState> = _mutableState.asStateFlow()

    private val _showSubjectTextFieldError = MutableStateFlow(false)
    val showSubjectTextFieldError = _showSubjectTextFieldError.asStateFlow()
    private var subjectText = ""

    private val _showEmailTextFieldError = MutableStateFlow(false)
    val showEmailTextFieldError = _showEmailTextFieldError.asStateFlow()
    private var emailText = ""

    private val _showDescriptionTextFieldError = MutableStateFlow(false)
    val showDescriptionTextFieldError = _showDescriptionTextFieldError.asStateFlow()
    private var descriptionText = ""


    fun updateSupject(text: String) {
        subjectText = text
        _showSubjectTextFieldError.value = false

    }

    fun updateEmail(text: String) {
        emailText = text
        _showEmailTextFieldError.value = false

    }

    fun updateDescription(text: String) {
        descriptionText = text
        _showDescriptionTextFieldError.value = false
    }

    fun sendComplaint() {
        if (subjectText.isEmpty()) {
            _showSubjectTextFieldError.value = true
        }
        if (emailText.isEmpty()) {
            _showEmailTextFieldError.value = true
        }
        if (descriptionText.isEmpty()) {
            _showDescriptionTextFieldError.value = true
        }
        if (!isValidEmail(emailText)) {
            _showEmailTextFieldError.value = true
        }
        if (!_showSubjectTextFieldError.value && !_showEmailTextFieldError.value && !_showDescriptionTextFieldError.value) {
            sendForm()
        }

    }

    fun sendForm() {
        if (config == null) return
        viewModelScope.launch {

            val data = api.submitData(
                url,
                config!!.appId,
                config!!.version,
                config!!.deviceId?:"",
                BuildConfig.LIB_VERSION_NAME,
                email = emailText,
                subject = subjectText,
                message = descriptionText,
            )

            when (data) {
                is NetworkResult.Success -> {

                    val response = data.value?.toDomain()
                    if (response == null || response.id == null) {
                        _mutableState.value = ViewModelState.NoContent
                    } else {
                        _mutableState.value = ViewModelState.Success(response)
                    }
                }

                is NetworkResult.Error -> {
                    when (data.error) {
                        is ApiError<*> -> {
                            val entity = data.error.errorEntity
                            if (entity is ErrorValidation) {
                                entity.errors?.email?.forEach { println("Email error: $it") }
                                entity.errors?.subject?.forEach { println("Subject error: $it") }
                                entity.errors?.message?.forEach { println("Message error: $it") }
                            }
                        }
                    }
                    _mutableState.value = ViewModelState.Error(data.error)
                }
            }
        }

    }

    private val _openDialog = MutableStateFlow(false)

    val openDialog: StateFlow<Boolean> = _openDialog.asStateFlow()

    fun showDialog() {
        _openDialog.value = true
    }

    fun dismissDialog() {
        _openDialog.value = false
        triggerForceUpdate()
    }


    private val _cancelButtonEvent = Channel<Unit>(Channel.BUFFERED)
    val cancelButtonEvent = _cancelButtonEvent.receiveAsFlow()

    fun triggerForceUpdate() {
        viewModelScope.launch {
            _cancelButtonEvent.send(Unit)
        }
    }
}