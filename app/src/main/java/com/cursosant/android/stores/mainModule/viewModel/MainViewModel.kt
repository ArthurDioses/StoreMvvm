package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.common.entities.StoreEntity
import com.cursosant.android.stores.mainModule.model.MainInteractor

class MainViewModel : ViewModel() {
    private val interactor: MainInteractor

    init {
        interactor = MainInteractor()
    }

    private val stores: MutableLiveData<List<StoreEntity>> by lazy {
        MutableLiveData<List<StoreEntity>>()/*.also { //descomentar con Corutines
            loadStores()
        }*/

    }

    fun getStores(): LiveData<List<StoreEntity>> {
        return stores.also { loadStores() }
    }

    private fun loadStores() {
        interactor.getStoresCallback(object : MainInteractor.StoresCallback {
            override fun getStoreCallback(stores: MutableList<StoreEntity>) {
                this@MainViewModel.stores.value = stores
            }
        })
    }
}