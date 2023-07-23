package br.com.francis.qrcode.app.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.francis.qrcode.R
import br.com.francis.qrcode.app.ui.common.Constants

@SuppressLint("ResourceType")
@Composable
fun BottomNavigation() {
    val applicationContext = LocalContext.current.applicationContext
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        ItemButton(
            Constants.statePage == Constants.Edit,
            Modifier.weight(1f),
            R.drawable.ic_qrcode_edit,
            R.string.edit,
            Constants.Edit
        )
        ItemButton(
            Constants.statePage == Constants.Scan,
            Modifier.fillMaxWidth(1f/3),
            R.drawable.ic_scan,
            R.string.scan,
            Constants.Scan
        )
        ItemButton(
            Constants.statePage == Constants.List,
            Modifier.weight(1f),
            R.drawable.ic_list,
            R.string.list,
            Constants.List
        )
    }
}

@Composable
@Preview
private fun BottomNavigationPreview() {
    BottomNavigation()
}

@SuppressLint("ResourceType")
@Composable
private fun ItemButton(
    selected: Boolean,
    modifier: Modifier,
    @DrawableRes idDraw: Int,
    @StringRes idText: Int,
    text:String,
    onClink:()->Unit = {}
) {
    TextButton(
        onClick = {
            onClink()
            Constants.statePage = text
        },
        shape = RectangleShape,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = idDraw),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = idText),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = selected,
                enter = expandHorizontally(),
                exit = shrinkHorizontally()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}