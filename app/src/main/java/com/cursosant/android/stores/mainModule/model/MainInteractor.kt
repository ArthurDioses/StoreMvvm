package com.cursosant.android.stores.mainModule.model

import com.cursosant.android.stores.StoreApplication
import com.cursosant.android.stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor {

    interface StoresCallback {
        fun getStoreCallback(stores: MutableList<StoreEntity>)
    }

    fun getStoresCallback(callback: StoresCallback) {
        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                callback.getStoreCallback(storeList)
            }
        }
    }
}