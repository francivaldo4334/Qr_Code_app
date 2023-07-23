package br.com.francis.qrcode.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TB_HISTORY")
class HistoryModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var Id:Int = 0,
    @ColumnInfo(name = "CONTENT")
    var Content:String
)