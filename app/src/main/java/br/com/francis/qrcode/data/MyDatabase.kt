package br.com.francis.qrcode.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.francis.qrcode.app.data.dao.HistoryDao
import br.com.francis.qrcode.app.data.model.HistoryModel

@Database(
    entities = arrayOf(
        HistoryModel::class
    ),
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao(): HistoryDao
}