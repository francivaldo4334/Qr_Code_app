package br.com.francis.qrcode.app.data.repository.implementation

import br.com.francis.qrcode.app.data.MyDatabase
import br.com.francis.qrcode.app.data.model.HistoryModel
import br.com.francis.qrcode.app.data.repository.contract.IHistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val db: MyDatabase) : IHistoryRepository {
    override fun getAll(): Flow<List<HistoryModel>> {
        return db.getDao().getAll()
    }

    override fun delete(Id: Int) {
        db.getDao().delete(Id)
    }

    override fun set(it: HistoryModel) {
        db.getDao().set(it)
    }
}