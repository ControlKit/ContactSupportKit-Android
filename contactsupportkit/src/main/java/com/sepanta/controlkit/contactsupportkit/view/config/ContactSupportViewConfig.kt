package com.sepanta.controlkit.contactsupportkit.view.config

import androidx.compose.material3.CardColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ContactSupportViewConfig(
    var viewStyle: ContactSupportViewStyle = ContactSupportViewStyle.Popover1,
    var updateImageDrawble:   Int? = null,
    var updateImageColor:   Color? = null,
    var imageLayoutModifier:   Modifier? = null,

    var popupViewLayoutModifier: Modifier? = null,
    var popupViewBackGroundColor: Color? = null,
    var popupViewCornerRadius: Dp? = null,
    var headerTitle: String = "Complaints",
    var headerLayoutModifier:   Modifier? = null,
    var buttonsLayoutModifier:   Modifier? = null,
    var submitButtonTitle: String = "Send",
    var cancelButtonTitle: String = "Cancel",
    var updateButtonTitleColor: Color? = null,
    var submitButtonColor: Color? = null,
    var cancelButtonColor: Color? = null,
    var cancelButtonCornerColor: Color? = null,
    var submitButtonCornerRadius: Dp? = null,
    var cancelButtonCornerRadius: Dp? = null,
    var updateButtonBorderColor: Color? = null,
    var submitButtonLayoutModifier:   Modifier? = null,
    var cancelButtonLayoutModifier:   Modifier? = null,

    var subjectLayoutModifier:   Modifier? = null,
    var subjectTitle: String = "Subject Complaints",
    var subjectTitleColor: Color? = null,
    var subjectTextFieldTextStyle: TextStyle? = null,
    var subjectTextFieldShape: Shape? = null,
    var subjectTextFieldColors: TextFieldColors? = null,
    var subjectTextFieldPlaceholderText: String = "Please Insert  subject",
    var subjectTextFieldPlaceholderView:  @Composable ((String) -> Unit)? = null,
    var subjectTextFieldSupportingText: String = "Complete this field",
    var subjectTextFieldModifier: Modifier? = null,
    var subjectTextFieldSupportingTextView: @Composable ((String) -> Unit)? = null,
    var subjectTitleView: @Composable ((String) -> Unit)? = null,

    var emailLayoutModifier:   Modifier? = null,
    var emailTitle: String = "E-Mail Address",
    var emailTitleColor: Color? = null,
    var emailTextFieldTextStyle: TextStyle? = null,
    var emailTextFieldShape: Shape? = null,
    var emailTextFieldColors: TextFieldColors? = null,
    var emailTextFieldPlaceholderText: String = "Please Insert You E-mail Address.",
    var emailTextFieldPlaceholderView: @Composable ((String) -> Unit)? = null,
    var emailTextFieldSupportingText: String = "Invalid email address (e.g. name@example.com)",
    var emailTextFieldModifier: Modifier? = null,
    var emailTextFieldSupportingTextView:  @Composable ((String) -> Unit)? = null,
    var emailTitleView: @Composable ((String) -> Unit)? = null,

    var descriptionLayoutModifier:   Modifier? = null,
    var descriptionTitle: String = "Description",
    var descriptionTitleColor: Color? = null,
    var descriptionTextFieldTextStyle: TextStyle? = null,
    var descriptionTextFieldShape: Shape? = null,
    var descriptionTextFieldColors: TextFieldColors? = null,
    var descriptionTextFieldPlaceholderText: String = "Please write your complaint in 500 characters.",
    var descriptionTextFieldPlaceholderView: @Composable ((String) -> Unit)? = null,
    var descriptionTextFieldSupportingText: String = "Complete this field",
    var descriptionTextFieldModifier: Modifier? = null,
    var descriptionTextFieldSupportingTextView: @Composable ((String) -> Unit)? = null,
    var descriptionTitleView: @Composable ((String) -> Unit)? = null,

    var imageView: @Composable ((String) -> Unit)? = null,
    var closeButtonView: @Composable ((String) -> Unit)? = null,
    var headerTitleView: @Composable ((String) -> Unit)? = null,
    var submitButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
    var cancelButtonView: (@Composable (libraryOnClick: () -> Unit) -> Unit)? = null,
)
