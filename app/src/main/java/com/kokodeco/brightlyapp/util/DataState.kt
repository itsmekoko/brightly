package com.kokodeco.brightlyapp.util

sealed class UIComponent {

    data class Toast(val message: String) : UIComponent()

}
