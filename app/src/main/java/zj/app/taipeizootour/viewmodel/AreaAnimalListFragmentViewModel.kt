package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zj.app.taipeizootour.db.data.AreaWithAnimals
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.hilt.qualifier.YYYYmmddHHmmssDateFormat
import zj.app.taipeizootour.repo.IZooRepo
import java.text.SimpleDateFormat

class AreaAnimalListFragmentViewModel @ViewModelInject constructor(
    sharedPreference: SharedPreferences,
    @YYYYmmddHHmmssDateFormat private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo,
    @Assisted private val savedStateHandle: SavedStateHandle
): AbsAreaDataListFragmentViewModel(sharedPreference, timeFormat, zooRepo, savedStateHandle) {

    private val selectedAnimal = MutableLiveData<ZooAnimal?>()

    val dataListLiveData = zooRepo.getLiveAreaAnimals(areaId)
    val selectedAnimalLiveData: LiveData<ZooAnimal?> = selectedAnimal

    override suspend fun doFetch(rid: String) {
        zooRepo.fetchAnimals(rid)
    }
}