package br.com.francis.qrcode

import android.app.Application
import androidx.room.Room
import br.com.francis.qrcode.app.data.MyDatabase
import br.com.francis.qrcode.app.data.repository.contract.IHistoryRepository
import br.com.francis.qrcode.app.data.repository.implementation.HistoryRepository

class MyApplication : Application() {
    lateinit var myDatabase: MyDatabase
    lateinit var repository: IHistoryRepository

    @Override
    override fun onCreate() {
        super.onCreate()
        myDatabase = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
        "DB_QR_CODE_STRINGS"
        ).fallbackToDestructiveMigration().build()
        repository = HistoryRepository(myDatabase)
    }
}