package zj.app.taipeizootour.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.state.ZooAreaState

class DetailActivityViewModel(
    private val zooRepo: IZooRepo
) : ViewModel() {

    private val areaWithPlants = MutableLiveData<AreaWithPlants?>()
    val areaWithPlantsLiveData: LiveData<AreaWithPlants?> = areaWithPlants

    fun fetchData(areaId: Int) {
        viewModelScope.launch {
            areaWithPlants.value = zooRepo.getAreaPlants(areaId)
        }
    }

}