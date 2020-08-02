package zj.app.taipeizootour.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.repo.IZooRepo

class DetailActivityViewModel(
    private val zooRepo: IZooRepo
) : ViewModel() {

    private val areaWithPlants = MutableLiveData<AreaWithPlants?>()
    private val selectedPlantId = MutableLiveData<ZooPlant?>()
    val areaWithPlantsLiveData: LiveData<AreaWithPlants?> = areaWithPlants
    val selectedPlantIdLiveData: LiveData<ZooPlant?> = selectedPlantId

    fun fetchData(areaId: Int) {
        viewModelScope.launch {
            areaWithPlants.value = zooRepo.getAreaPlants(areaId)
        }
    }

    fun selectPlant(plant: ZooPlant) {
        selectedPlantId.value = plant
    }
}