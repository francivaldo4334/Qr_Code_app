package br.com.francis.qrcode.app.ui.common

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Constants {
    var openError by mutableStateOf(false)
    var openSuccess by mutableStateOf(false)
    var TextResultScan by mutableStateOf("")
    val Edit = "Edit"
    val Scan = "Scan"
    val List = "List"
    var statePage by mutableStateOf(Edit)
    var ImageQrCode: Bitmap? by mutableStateOf(null)
}