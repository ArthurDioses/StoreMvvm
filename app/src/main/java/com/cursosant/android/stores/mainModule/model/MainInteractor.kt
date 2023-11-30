package com.cursosant.android.stores.mainModule.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.cursosant.android.stores.StoreApplication
import com.cursosant.android.stores.common.entities.StoreEntity
import com.cursosant.android.stores.common.utils.Constants
import com.cursosant.android.stores.common.utils.StoresException
import com.cursosant.android.stores.common.utils.TypeError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor {

    /*
    fun getStores(callback: (MutableList<StoreEntity>) -> Unit) {
        val isLocal = true
        if (isLocal) {
            getStoresRoom { storeList ->
                callback(storeList)
            }
        } else {
            getStoresAPI { storeList ->
                callback(storeList)
            }
        }
    }
    */

    /*
    fun getStoresAPI(callback: (MutableList<StoreEntity>) -> Unit) {
        val url = Constants.STORES_URL + Constants.GET_ALL_PATH

        val storeList = mutableListOf<StoreEntity>()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val status = response.optInt(Constants.STATUS_PROPERTY, Constants.ERROR)
            Log.i("status", status.toString())
            if (status == Constants.SUCCESS) {
                val jsonList = response.optJSONArray(Constants.STORES_PROPERTY)?.toString()
                val mutableListType = object : TypeToken<MutableList<StoreEntity>>() {}.type
                if (!jsonList.isNullOrEmpty()) {
                    val storeList =
                        Gson().fromJson<MutableList<StoreEntity>>(jsonList, mutableListType)
                    callback(storeList)
                    return@JsonObjectRequest
                }
                callback(storeList)
            }
            callback(storeList)

        }, {
            it.printStackTrace()
            callback(storeList)
        })

        StoreApplication.storeAPI.addToRequestQueue(jsonObjectRequest)
    }
    */

    /*
    fun getStoresRoom(callback: (MutableList<StoreEntity>) -> Unit) {
        doAsync {
            val storeList = StoreApplication.database.storeDao().getAllStores()
            uiThread {
                val json = Gson().toJson(storeList)
                Log.i("Gson", json)
                callback(storeList)
            }
        }
    }
     */

    val stores: LiveData<MutableList<StoreEntity>> = liveData {
        delay(1_000)//Temporal to test
        val storesLiveData = StoreApplication.database.storeDao().getAllStores()
        emitSource(storesLiveData.map { stores ->
            stores.sortedBy { it.name }.toMutableList()

        })
    }

    suspend fun deleteStore(storeEntity: StoreEntity) {
        delay(1_500)
        val result = StoreApplication.database.storeDao().deleteStore(storeEntity)
        if (result == 0) throw StoresException(TypeError.DELETE)
    }

    suspend fun updateStore(storeEntity: StoreEntity) {
        delay(300)
        val result = StoreApplication.database.storeDao().updateStore(storeEntity)
        if (result == 0) throw StoresException(TypeError.UPDATE)
    }
}