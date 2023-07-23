package br.com.francis.qrcode

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ActionMode
import com.journeyapps.barcodescanner.CaptureActivity

class ScannerQRCodeActivity : CaptureActivity() {
    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}