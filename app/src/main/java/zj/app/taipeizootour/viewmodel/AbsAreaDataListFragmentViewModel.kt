package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.repo.IZooRepo
import java.text.SimpleDateFormat

abstract class AbsAreaDataListFragmentViewModel constructor(
    private val sharedPreference: SharedPreferences,
    private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val tagName = javaClass.simpleName

    protected val areaId: Int = savedStateHandle[zj.app.taipeizootour.const.Constants.ARG_KEY_AREA_ID]
        ?: throw IllegalArgumentException("missing areaId")

    protected abstract suspend fun doFetch(rid: String)

    suspend fun fetchFromApi(query: String, dataTimePrefKey: String) {
        try {
            val animalMeta = getNewMeta(query, dataTimePrefKey)
            animalMeta?.resources?.firstOrNull()?.resourceId?.let { rid ->
                doFetch(rid)
            }
        } catch (e: Exception) {
            Log.d(tagName, e.message , e)
        }
    }

    private suspend fun getNewMeta(query: String, dataTimePrefKey: String): DataSetMetadata? {
        return withContext(Dispatchers.IO) {
            zooRepo.fetchMeta(query)?.takeIf { meta ->
                val dataTime = timeFormat.parse(meta.metadataModified)?.time ?: -1L
                val originalDataTime = sharedPreference.getLong(dataTimePrefKey, -1L)
                val hasUpdate = dataTime > originalDataTime
                if (hasUpdate) {
                    sharedPreference.edit {
                        putLong(dataTimePrefKey, dataTime)
                    }
                }
                return@takeIf hasUpdate
            }
        }
    }
}