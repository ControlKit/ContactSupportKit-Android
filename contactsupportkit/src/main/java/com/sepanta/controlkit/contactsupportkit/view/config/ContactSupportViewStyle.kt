package com.sepanta.controlkit.contactsupportkit.view.config

import com.sepanta.controlkit.contactsupportkit.view.ui.ContactSupportFullScreen1
import com.sepanta.controlkit.contactsupportkit.view.ui.ContactSupportPopover1
import com.sepanta.controlkit.contactsupportkit.view.ui.ContactSupportPopover2


enum class ContactSupportViewStyle {
    FullScreen1,
    Popover1,
    Popover2;
    companion object {
        fun checkViewStyle(style: ContactSupportViewStyle): ContactSupportContract {
            return when (style) {
                FullScreen1 -> ContactSupportFullScreen1()
                Popover1 -> ContactSupportPopover1()
                Popover2 -> ContactSupportPopover2()
            }

        }

    }

}
