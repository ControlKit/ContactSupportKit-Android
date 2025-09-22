package com.sepanta.controlkit.testcontactsupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sepanta.controlkit.contactsupportkit.ContactSupportKit
import com.sepanta.controlkit.contactsupportkit.config.ContactSupportServiceConfig
import com.sepanta.controlkit.contactsupportkit.contactSupportKitHost
import com.sepanta.controlkit.contactsupportkit.service.model.ContactSupportResponse
import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportViewConfig
import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportViewStyle
import com.sepanta.controlkit.contactsupportkit.view.viewModel.ContactSupportViewModel
import com.sepanta.controlkit.testcontactsupport.ui.theme.TestContactSupportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            TestContactSupportTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    val kit = contactSupportKitHost(
                        ContactSupportServiceConfig(
                            version = "1.0.0",
                            appId = "9fee1663-e80e-46ad-8cd9-357263375a9c",
                            viewConfig = ContactSupportViewConfig(
                                ContactSupportViewStyle.Popover1
                            )
                        ),
                        onDismiss = {

                        }
                    )
                    kit.showView()
                }
            }
        }
    }
}

