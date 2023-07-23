package br.com.francis.qrcode.app.data.repository.contract

import br.com.francis.qrcode.app.data.model.HistoryModel
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {
    fun getAll(): Flow<List<HistoryModel>>
    fun delete(Id:Int)
    fun set(it: HistoryModel)
}