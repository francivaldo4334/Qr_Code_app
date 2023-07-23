package br.com.francis.qrcode.app.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.francis.qrcode.R

@Composable
fun ScanQrCode(onStartScan:()->Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val paddingAnimation by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = 56.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(72.dp)
            .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ){
            Box(modifier = Modifier
                .padding(paddingAnimation)
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background.copy(0.5f))
                .aspectRatio(1f),
            )
            Box(modifier = Modifier
                .padding(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .aspectRatio(1f)
                .clickable{
                    onStartScan()
                },
                contentAlignment = Alignment.Center
            ){
                Text(stringResource(R.string.escanear),color = MaterialTheme.colorScheme.primary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
@Preview
fun ScanQrCodePreview(){
    ScanQrCode(){}
}
@Composable
fun MyIconButton(
    @DrawableRes IdRes: Int,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Icon(
            painter = painterResource(id = IdRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.background
        )
    }
}
