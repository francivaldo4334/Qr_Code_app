package br.com.francis.qrcode.app.ui.component

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.francis.qrcode.R
import br.com.francis.qrcode.app.ui.common.Constants
import br.com.francis.qrcode.app.ui.screen.MyIconButton

@Composable
fun DialogSuccessScan(show:Boolean,onDismiss:()->Unit) {
    val context = LocalContext.current
    val resultQRCode = stringResource(R.string.result_qrcode)
    val CopyToTransfer = stringResource(R.string.copy_to_transfer_place)
    if(show) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Text(text = "Result", fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = Constants.TextResultScan,color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    MyIconButton(
                        R.drawable.ic_share,
                        Modifier.weight(1f)
                    ) {
                        val textToShare = Constants.TextResultScan
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)

                        try {
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Compartilhar via"
                                )
                            )
                        } catch (e: Exception) {
                            // Tratar o caso de nenhum aplicativo para compartilhamento ser encontrado
                            Toast.makeText(
                                context,
                                "Nenhum aplicativo disponível para compartilhamento.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    MyIconButton(
                        R.drawable.ic_copy,
                        Modifier.weight(1f)
                    ) {
                        val clipboardManager =
                            context.getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText(resultQRCode, Constants.TextResultScan)
                        clipboardManager.setPrimaryClip(clipData)
                        Toast.makeText(
                            context,
                            "${Constants.TextResultScan} : " + CopyToTransfer,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
@Composable
@Preview
fun DialogSuccessScanPreview(){
    DialogSuccessScan(true,{})
}