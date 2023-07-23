package br.com.francis.qrcode

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.francis.qrcode.app.service.QrCodeService
import br.com.francis.qrcode.app.ui.common.Constants
import br.com.francis.qrcode.app.ui.component.BottomNavigation
import br.com.francis.qrcode.app.ui.component.DialogErrorScan
import br.com.francis.qrcode.app.ui.component.DialogSuccessScan
import br.com.francis.qrcode.app.ui.screen.EditQrCode
import br.com.francis.qrcode.app.ui.screen.ListStrings
import br.com.francis.qrcode.app.ui.screen.ScanQrCode
import br.com.francis.qrcode.app.ui.theme.QrCodeTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    companion object{
        private var myViewModel: MyViewModel? = null
        fun getMyViewModel(): MyViewModel {
            if(myViewModel == null){
                throw Exception()
            }
            return myViewModel!!
        }
    }
    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result->
        if (result.contents == null) {
            Constants.openError = true
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Constants.TextResultScan = result.contents
            Constants.openSuccess = true
            getMyViewModel().setHistory(result.contents)
            Toast.makeText(
                this,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1
    fun checkWriteExternalStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
            return false
        }
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var qrServ = QrCodeService()
        val textDefault = getString(R.string.insira_um_texto)
        Constants.ImageQrCode = qrServ.generateQRCode(textDefault,512,512)
        myViewModel = MyViewModel(
            ((applicationContext) as MyApplication).repository
        )
        setContent {
            QrCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    val lsHistory by getMyViewModel().lsHistory.collectAsState()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AnimatedVisibility(
                            visible = Constants.statePage == Constants.Edit,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            EditQrCode(
                                qrServ
                            ) {
                                if (checkWriteExternalStoragePermission()) {

                                    val root = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                    val appFolder = File(getExternalFilesDir(null), "NomeDoApp") // Substitua "NomeDoApp" pelo nome do seu aplicativo
                                    appFolder.mkdirs()
                                    val fileDir = File(root?.absolutePath+"/${getString(R.string.app_name)}")
                                    fileDir.mkdirs()
                                    val timeStamp = SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.getDefault()).format(
                                        Date()
                                    )
                                    val fileName = "image_rr_code_$timeStamp.png"
                                    val file = File(fileDir, fileName)
                                    try {
                                        val fileOutputStream = FileOutputStream(file)
                                        Constants.ImageQrCode?.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                                        fileOutputStream.flush()
                                        fileOutputStream.close()
                                        Toast.makeText(this@MainActivity,"Image : ${fileName} Save in ${fileDir}!",Toast.LENGTH_LONG).show()
                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = Constants.statePage == Constants.Scan,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            ScanQrCode(){
                                val scanOptions = ScanOptions()
                                scanOptions.setPrompt("")
                                scanOptions.setBeepEnabled(true)
                                scanOptions.setOrientationLocked(true)
                                scanOptions.setCaptureActivity(ScannerQRCodeActivity::class.java)
                                barcodeLauncher.launch(scanOptions)
                            }
                        }
                        AnimatedVisibility(
                            visible = Constants.statePage == Constants.List,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            ListStrings(lsHistory)
                        }
                        BottomNavigation()
                    }
                    DialogErrorScan(show = Constants.openError) {
                        Constants.openError = false
                    }
                    DialogSuccessScan(show = Constants.openSuccess) {
                        Constants.openSuccess = false
                    }
                }
            }
        }
    }
}