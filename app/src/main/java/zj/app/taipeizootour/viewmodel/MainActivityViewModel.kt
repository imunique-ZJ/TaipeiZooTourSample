package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.state.ZooAreaState
import java.text.SimpleDateFormat

class MainActivityViewModel(
    private val sharedPreference: SharedPreferences,
    private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo
) : ViewModel() {

    val state = MutableLiveData<ZooAreaState>(ZooAreaState.Loading)

    val areaLiveData = zooRepo.getLiveArea()

    fun fetchData(areaIntroQuery: String, plantsQuery: String) {
        viewModelScope.launch {
            state.value = ZooAreaState.Loading
            val originalAreaIntroDataTime = sharedPreference.getLong(Constants.KEY_SP_ZOO_AREA_INTRO_DATE_TIME, -1L)
            val areaIntroMeta = getNewMeta(areaIntroQuery, originalAreaIntroDataTime)
            val areaIntroRid = areaIntroMeta?.resources?.firstOrNull()?.resourceId
            if (areaIntroRid != null) {
                zooRepo.fetchAreaIntro(areaIntroMeta.id, areaIntroRid)
                val originalPlantsDataTime = sharedPreference.getLong(Constants.KEY_SP_ZOO_PLANTS_DATE_TIME, -1L)
                val plantsMeta = getNewMeta(plantsQuery, originalPlantsDataTime)
                plantsMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                    zooRepo.fetchPlants(plantsRid)
                }
            }
            state.value = ZooAreaState.Finish
        }
    }

    private suspend fun getNewMeta(query: String, originalDataTime: Long): DataSetMetadata? {
        return withContext(Dispatchers.IO) {
            zooRepo.fetchMeta(query)?.takeIf { meta ->
                val dataTime = timeFormat.parse(meta.metadataModified)?.time ?: -1L
                dataTime > originalDataTime
            }
        }
    }
}