package br.com.francis.qrcode.app.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import br.com.francis.qrcode.MainActivity
import br.com.francis.qrcode.R
import br.com.francis.qrcode.app.service.QrCodeService
import br.com.francis.qrcode.app.ui.common.Constants
import br.com.francis.qrcode.app.ui.component.DottedLine
import br.com.francis.qrcode.app.ui.component.RoundedBiteShapeBottom
import br.com.francis.qrcode.app.ui.component.RoundedBiteShapeTop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQrCode(qrServ: QrCodeService, onSaveQrCode: () -> Unit) {
    val textDefault = stringResource(R.string.insira_um_texto)
    var textValue by remember{ mutableStateOf(textDefault) }
    val context = LocalContext.current
    val message_string_empty = stringResource(R.string.o_texto_esta_invalido)
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 82.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedBiteShapeTop(32.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            //Image qr code
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 16.dp)
            ) {
                TextField(
                    value = textValue,
                    onValueChange = {
                        textValue = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if(textValue.trim().isEmpty()){
                            Toast.makeText(context,message_string_empty,Toast.LENGTH_LONG).show()
                            return@KeyboardActions
                        }
                        Constants.ImageQrCode = qrServ.generateQRCode(textValue,512,512)
                        MainActivity.getMyViewModel().setHistory(textValue)
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Black.copy(0.19f),
                        textColor = Color.Black.copy(0.64f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            if (it.isFocused)
                                textValue = ""
                        },
                    shape = RoundedCornerShape(14.dp)
                )
                IconButton(onClick = onSaveQrCode) {
                    Icon(
                        painterResource(id = R.drawable.ic_download),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            }
            DottedLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedBiteShapeBottom(32.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            //Image qr code
            if(Constants.ImageQrCode == null){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                        .aspectRatio(1f)
                        .border(1.dp, Color.Black)
                )
            }
            else{
                Image(
                    Constants.ImageQrCode!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                        .aspectRatio(1f)
                        .background(Color.Black)
                )
            }
            DottedLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }
    }
}