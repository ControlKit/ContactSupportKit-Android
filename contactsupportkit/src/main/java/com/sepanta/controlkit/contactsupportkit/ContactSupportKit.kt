package com.sepanta.controlkit.contactsupportkit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sepanta.controlkit.contactsupportkit.config.ContactSupportServiceConfig
import com.sepanta.controlkit.contactsupportkit.service.ApiService
import com.sepanta.controlkit.contactsupportkit.service.ContactSupportApi
import com.sepanta.controlkit.contactsupportkit.service.RetrofitClientInstance
import com.sepanta.controlkit.contactsupportkit.utils.UniqueUserIdProvider
import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportViewStyle
import com.sepanta.controlkit.contactsupportkit.view.viewModel.ContactSupportViewModel
import com.sepanta.controlkit.contactsupportkit.view.viewModel.ContactSupportViewModelFactory
import com.sepanta.controlkit.contactsupportkit.view.viewModel.state.ViewModelState

class ContactSupportKit(
    private var config: ContactSupportServiceConfig,
    context: Context,

    ) {

    private var _viewModel: ContactSupportViewModel? = null

    init {
        setupViewModel(context)
    }

    private fun setupViewModel(context: Context) {
        val retrofit = RetrofitClientInstance.getRetrofitInstance(
            config.timeOut,
            config.maxRetry,
            config.timeRetryThreadSleep
        ) ?: return

        val api = ContactSupportApi(retrofit.create(ApiService::class.java))
        _viewModel = ContactSupportViewModelFactory(api).create(ContactSupportViewModel::class.java)
        if (config.deviceId == null) {
            config.deviceId = UniqueUserIdProvider.getOrCreateUserId(context)
            _viewModel?.setConfig(config)

        } else {
            _viewModel?.setConfig(config)

        }
    }


    @Composable
    internal fun ConfigureComposable(
        onDismiss: (() -> Unit)? = null,
        onState: ((ViewModelState) -> Unit)? = null,
    ) {
        val state = _viewModel?.state?.collectAsState()?.value

        LaunchedEffect(Unit) {
            _viewModel?.cancelButtonEvent?.collect {
                onDismiss?.invoke()
            }
        }

        when (state) {
            ViewModelState.Initial -> {
                ContactSupportViewStyle.checkViewStyle(config.viewConfig.viewStyle)
                    .ShowView(config = config.viewConfig, _viewModel!!)
                onState?.invoke(ViewModelState.Initial)
            }

            ViewModelState.NoContent -> onState?.invoke(ViewModelState.NoContent)

            is ViewModelState.Success -> state.data?.let {
                onState?.invoke(ViewModelState.Success(it))
            }

            is ViewModelState.Error -> onState?.invoke(ViewModelState.Error(state.data))

            else -> Unit
        }
    }

    fun showView() {
        _viewModel?.showDialog()
    }
}

@Composable
fun contactSupportKitHost(
    config: ContactSupportServiceConfig,
    onDismiss: (() -> Unit)? = null,
    onState: ((ViewModelState) -> Unit)? = null,
): ContactSupportKit {
    val context = LocalContext.current

    val kit = remember { ContactSupportKit(config, context) }
    kit.ConfigureComposable(onState = onState, onDismiss = onDismiss)
    return kit
}





