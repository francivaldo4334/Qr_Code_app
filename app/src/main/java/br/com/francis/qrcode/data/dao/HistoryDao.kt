package br.com.francis.qrcode.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.francis.qrcode.app.data.model.HistoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM TB_HISTORY")
    fun getAll(): Flow<List<HistoryModel>>
    @Query("DELETE FROM TB_HISTORY WHERE Id = :Id")
    fun delete(Id:Int)
    @Insert
    fun set(it: HistoryModel)
}