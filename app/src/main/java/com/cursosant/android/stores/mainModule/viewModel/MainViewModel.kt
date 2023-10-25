package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.StoreApplication
import com.cursosant.android.stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainViewModel : ViewModel() {
    private val stores: MutableLiveData<List<StoreEntity>>

    init {
        stores = MutableLiveData()
    }

    fun getStores(): LiveData<List<StoreEntity>> {
        return stores
    }

    private fun loadStores() {

        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                stores.value = storeList
            }
        }
    }
}