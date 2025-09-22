package com.sepanta.controlkit.contactsupportkit.view.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sepanta.controlkit.contactsupportkit.service.model.ContactSupportResponse
import com.sepanta.controlkit.contactsupportkit.theme.Black100
import com.sepanta.controlkit.contactsupportkit.theme.Black50
import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportContract
import com.sepanta.controlkit.contactsupportkit.view.config.ContactSupportViewConfig
import com.sepanta.controlkit.contactsupportkit.view.viewModel.ContactSupportViewModel
import com.sepanta.controlkit.contactsupportkit.theme.Black60
import com.sepanta.controlkit.contactsupportkit.theme.Blue20
import com.sepanta.controlkit.contactsupportkit.theme.Typography
import com.sepanta.controlkit.contactsupportkit.theme.White100

class ContactSupportPopover2 : ContactSupportContract {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ShowView(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        val openDialog = viewModel.openDialog.collectAsState()
        val sheetState =
            rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = {
                openDialog.value
            })

        if (!openDialog.value) return

        ModalBottomSheet(
            onDismissRequest = { viewModel.dismissDialog() },
            sheetState = sheetState,
            contentWindowInsets = { WindowInsets.captionBar },
            dragHandle = null,

            shape = RoundedCornerShape(config.popupViewCornerRadius ?: 15.dp),
            containerColor = White100,

            ) {

            Surface(
                modifier = config.popupViewLayoutModifier ?: Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(), color = config.popupViewBackGroundColor ?: White100
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        config.headerLayoutModifier ?: Modifier
                            .padding(
                                top = 40.dp,
                            )
                            .fillMaxWidth()
                            .padding(start = 14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        HeaderTitle(config)
                    }
                    SubjectLayout(config, viewModel)
                    EmailLayout(config, viewModel)
                    DescriptionLayout(config, viewModel, Modifier.weight(1f))
                    Buttons(config, viewModel)
                }

            }

        }

    }


    @Composable
    private fun HeaderTitle(
        config: ContactSupportViewConfig
    ) {
        config.headerTitleView?.let { textView ->
            textView( config.headerTitle)
        } ?: Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 10.dp),
            text =  config.headerTitle,
            style = Typography.titleLarge,
            color = config.subjectTitleColor ?: Typography.titleLarge.color

        )


    }

    @Composable
    private fun SubjectLayout(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        Column(
            modifier = config.subjectLayoutModifier ?: Modifier
                .padding(
                    top = 26.dp, start = 24.dp, end = 24.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            SubjectTitle(config)
            SubjectTextField(config, viewModel)

        }
    }

    @Composable
    private fun SubjectTitle(
        config: ContactSupportViewConfig
    ) {

        config.subjectTitleView ?: Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 8.dp),
            text = config.subjectTitle,
            style = Typography.bodyMedium,
            color = config.subjectTitleColor ?: Typography.bodyMedium.color

        )


    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SubjectTextField(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        val showError by viewModel.showSubjectTextFieldError.collectAsState()
        var text by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = config.subjectTextFieldModifier ?: Modifier
                .defaultMinSize(minHeight = 46.dp)
                .heightIn(max = 76.dp)
                .fillMaxWidth()
                .padding(top = 15.dp),
            value = text,
            singleLine = true,
            placeholder = {
                config.subjectTextFieldPlaceholderView?.let { textView ->
                    textView(config.subjectTextFieldPlaceholderText)
                } ?: Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = config.subjectTextFieldPlaceholderText,
                    color = Black60,
                    style = Typography.titleSmall
                )
            },
            textStyle = config.subjectTextFieldTextStyle ?: Typography.titleSmall,
            shape = config.subjectTextFieldShape ?: RoundedCornerShape(20.dp),
            colors = config.subjectTextFieldColors ?: OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Blue20, unfocusedBorderColor = Blue20
            ),
            isError = showError,
            supportingText = {
                if (showError) {
                    config.subjectTextFieldSupportingTextView?.let { textView ->
                        textView(config.subjectTextFieldSupportingText)
                    } ?:  Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = config.subjectTextFieldSupportingText,
                        color = MaterialTheme.colorScheme.error
                    )
                }

            },
            onValueChange = {
                text = it
                viewModel.updateSupject(text)
            },
        )
    }


    @Composable
    private fun EmailLayout(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        Column(
            modifier = config.emailLayoutModifier ?: Modifier
                .padding(
                    top = 10.dp, start = 24.dp, end = 24.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            EmailTitle(config)
            EmailTextField(config, viewModel)

        }
    }

    @Composable
    private fun EmailTitle(
        config: ContactSupportViewConfig
    ) {

        config.emailTitleView ?: Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 8.dp),
            text = config.emailTitle,
            style = Typography.bodyMedium,
            color = config.emailTitleColor ?: Typography.bodyMedium.color

        )


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EmailTextField(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        val showError by viewModel.showEmailTextFieldError.collectAsState()
        var text by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = config.emailTextFieldModifier ?: Modifier
                .defaultMinSize(minHeight = 46.dp)
                .heightIn(max = 76.dp)
                .fillMaxWidth()
                .padding(top = 15.dp),
            value = text,
            singleLine = true,
            placeholder = {
                config.emailTextFieldPlaceholderView?.let { textView ->
                    textView( config.emailTextFieldPlaceholderText)
                } ?: Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = config.emailTextFieldPlaceholderText,
                    color = Black60,
                    style = Typography.titleSmall
                )
            },
            textStyle = config.emailTextFieldTextStyle ?: Typography.titleSmall,
            shape = config.emailTextFieldShape ?: RoundedCornerShape(20.dp),
            colors = config.subjectTextFieldColors ?: OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Blue20, unfocusedBorderColor = Blue20
            ),
            isError = showError,
            supportingText = {
                if (showError) {
                    config.emailTextFieldSupportingTextView?.let { textView ->
                        textView( config.emailTextFieldSupportingText)
                    } ?: Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = config.emailTextFieldSupportingText,
                        color = MaterialTheme.colorScheme.error
                    )
                }


            },
            onValueChange = {
                text = it
                viewModel.updateEmail(text)
            },
        )
    }


    @Composable
    private fun DescriptionLayout(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel,
        modifier: Modifier

    ) {
        Column(
            modifier = config.descriptionLayoutModifier ?: modifier
                .padding(
                    top = 10.dp, start = 24.dp, end = 24.dp
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            DescriptionTitle(config)
            DescriptionTextField(config, viewModel)

        }
    }

    @Composable
    private fun DescriptionTitle(
        config: ContactSupportViewConfig
    ) {

        config.descriptionTitleView ?: Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 8.dp),
            text = config.descriptionTitle,
            style = Typography.bodyMedium,
            color = config.descriptionTitleColor ?: Typography.bodyMedium.color
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DescriptionTextField(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel
    ) {
        val showError by viewModel.showDescriptionTextFieldError.collectAsState()
        var text by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = config.descriptionTextFieldModifier
                ?: Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
            value = text,
            singleLine = false,
            placeholder = {
                config.descriptionTextFieldPlaceholderView?.let { textView ->
                    textView( config.descriptionTextFieldPlaceholderText)
                } ?:  Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = config.descriptionTextFieldPlaceholderText,
                    color = Black60,
                    style = Typography.titleSmall
                )
            },
            textStyle = config.descriptionTextFieldTextStyle ?: Typography.titleSmall,
            shape = config.descriptionTextFieldShape ?: RoundedCornerShape(20.dp),
            colors = config.subjectTextFieldColors ?: OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Blue20, unfocusedBorderColor = Blue20
            ),
            isError = showError,
            supportingText = {
                if (showError) {
                    config.descriptionTextFieldSupportingTextView?.let { textView ->
                        textView( config.descriptionTextFieldSupportingText)
                    } ?:  Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = config.descriptionTextFieldSupportingText,
                        color = MaterialTheme.colorScheme.error
                    )
                }

            },
            onValueChange = {
                text = it
                viewModel.updateDescription(text)
            },
        )
    }

    @Composable
    private fun Buttons(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel,

    ) {
        Row(
            modifier = config.buttonsLayoutModifier ?: Modifier.padding(
                start = 24.dp, end = 24.dp, bottom = 34.dp, top = 26.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonCancel(config, viewModel)
            Spacer(modifier = Modifier.width(5.dp))
            ButtonSend(config, viewModel)

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RowScope.ButtonSend(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel

    ) {
        val onClickAction: () -> Unit = {
            viewModel.sendComplaint()

        }

        config.submitButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = RoundedCornerShape(config.submitButtonCornerRadius ?: 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.submitButtonColor ?: Black50
            ),
            modifier = config.submitButtonLayoutModifier ?: Modifier
                .weight(1f)
                .height(52.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text =  config.submitButtonTitle,
                style = Typography.titleMedium,
                color = Black100
            )
        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RowScope.ButtonCancel(
        config: ContactSupportViewConfig,
        viewModel: ContactSupportViewModel,

    ) {


        val onClickAction: () -> Unit = {
            viewModel.dismissDialog()
        }
        config.cancelButtonView?.let { button ->
            button(onClickAction)
        } ?: Button(
            onClick = onClickAction,
            shape = RoundedCornerShape(config.cancelButtonCornerRadius ?: 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = config.cancelButtonColor ?: Color.Transparent
            ),
            border = BorderStroke(1.dp, Black50),
            modifier = config.cancelButtonLayoutModifier ?: Modifier
                .weight(1f)
                .height(52.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text =  config.cancelButtonTitle,
                style = Typography.titleMedium,
                color = Black60
            )
        }


    }
}