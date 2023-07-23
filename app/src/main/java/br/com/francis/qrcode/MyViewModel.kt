package br.com.francis.qrcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francis.qrcode.app.data.model.HistoryModel
import br.com.francis.qrcode.app.data.repository.contract.IHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyViewModel constructor(
    private val repository: IHistoryRepository
):ViewModel(){
    fun setHistory(text: String) {
        viewModelScope.launch (Dispatchers.IO){
            repository.set(
                HistoryModel(
                    Content = text
                )
            )
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }

    val lsHistory = repository
        .getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )
}