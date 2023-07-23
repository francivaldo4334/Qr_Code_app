package br.com.francis.qrcode.app.ui.screen

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.francis.qrcode.MainActivity
import br.com.francis.qrcode.R
import br.com.francis.qrcode.app.data.model.HistoryModel
import br.com.francis.qrcode.app.ui.component.DialogDeleteHistory

@Composable
fun ListStrings(lsHistory: List<HistoryModel>) {
    val initPair = Pair(false,0)
    var openDelete by remember { mutableStateOf(initPair)}
    val context = LocalContext.current
    val resultQRCode = stringResource(R.string.result_qrcode)
    val CopyToTransfer = stringResource(R.string.copy_to_transfer_place)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 98.dp, top = 56.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement =
        if(lsHistory.isEmpty())
            Arrangement.Center
        else
            Arrangement.Top
    ) {
        if(lsHistory.isEmpty()){
            item { 
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painter = painterResource(id = R.drawable.empty_box), contentDescription = null)
                    Text(text = stringResource(R.string.no_history))
                }
            }
        }
        else {
            items(lsHistory) {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clickable {
                                val clipboardManager =
                                    context.getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
                                val clipData = ClipData.newPlainText(resultQRCode, it.Content)
                                clipboardManager.setPrimaryClip(clipData)
                                Toast
                                    .makeText(
                                        context,
                                        "${it.Content} : " + CopyToTransfer,
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = it.Content,
                                maxLines = 1,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            openDelete = Pair(true, it.Id)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = null, tint = Color.Black)
                        }
                    }
                    Divider()
                }
            }
        }
    }
    DialogDeleteHistory(
        openDelete.first,
        onDismiss = {
            openDelete = initPair
        }
    ){
        MainActivity.getMyViewModel().delete(openDelete.second)
    }
}