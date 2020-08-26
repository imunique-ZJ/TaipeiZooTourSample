package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.hilt.qualifier.YYYYmmddHHmmssDateFormat
import zj.app.taipeizootour.repo.IZooRepo
import java.text.SimpleDateFormat

class AreaPlantListFragmentViewModel @ViewModelInject constructor(
    private val sharedPreference: SharedPreferences,
    @YYYYmmddHHmmssDateFormat private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo,
    @Assisted private val savedStateHandle: SavedStateHandle
): AbsAreaDataListFragmentViewModel(sharedPreference, timeFormat, zooRepo, savedStateHandle) {

    private val selectedPlant = MutableLiveData<ZooPlant?>()

    val dataListLiveData = zooRepo.getLiveAreaPlants(areaId)
    val selectedPlantLiveData: LiveData<ZooPlant?> = selectedPlant

    override suspend fun doFetch(rid: String) {
        zooRepo.fetchPlants(rid)
    }

}