package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.data.DerivedZooAnimal
import zj.app.taipeizootour.data.DerivedZooPlant
import zj.app.taipeizootour.data.transformer.IDataTransformer
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.hilt.qualifier.YYYYmmddHHmmssDateFormat
import zj.app.taipeizootour.hilt.qualifier.ZooAnimalTransformerQualifier
import zj.app.taipeizootour.hilt.qualifier.ZooPlantTransformerQualifier
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.state.ZooAreaState
import java.text.SimpleDateFormat

class MainActivityViewModel @ViewModelInject constructor(
    private val sharedPreference: SharedPreferences,
    @YYYYmmddHHmmssDateFormat private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo,
    @ZooAnimalTransformerQualifier private val zooAnimalTransformer: @JvmSuppressWildcards IDataTransformer<ZooAnimal, DerivedZooAnimal>,
    @ZooPlantTransformerQualifier private val zooPlantTransformer: @JvmSuppressWildcards IDataTransformer<ZooPlant, DerivedZooPlant>
) : ViewModel() {

    private val tagName = javaClass.simpleName
    private val selectedArea = MutableLiveData<ZooArea?>()
    private val selectedAnimal = MutableLiveData<ZooAnimal?>()
    private val selectedPlant = MutableLiveData<ZooPlant?>()

    val state = MutableLiveData<ZooAreaState>(ZooAreaState.Loading)
    val areaLiveData = zooRepo.getLiveArea()
    val selectedAreaLiveData: LiveData<ZooArea?> = selectedArea
    val selectedAnimalLiveData: LiveData<DerivedZooAnimal?> = selectedAnimal.switchMap {
        liveData {
            emit(zooAnimalTransformer.transform(it))
        }
    }
    val selectedPlantLiveData: LiveData<DerivedZooPlant?> = selectedPlant.switchMap {
        liveData {
            emit(zooPlantTransformer.transform(it))
        }
    }

    fun fetchData(areaIntroQuery: String, plantsQuery: String, animalsQuery: String) {
        viewModelScope.launch {
            state.value = ZooAreaState.Loading
            val areaIntroMeta = getNewMeta(areaIntroQuery, Constants.KEY_SP_ZOO_AREA_INTRO_DATE_TIME)
            val areaIntroRid = areaIntroMeta?.resources?.firstOrNull()?.resourceId
            if (areaIntroRid != null) {
                zooRepo.fetchAreaIntro(areaIntroMeta.id, areaIntroRid)
                awaitAll(
                    async { fetchPlantsInfo(plantsQuery) },
                    async { fetchAnimalsInfo(animalsQuery) }
                )
            }
            state.value = ZooAreaState.Finish
        }
    }

    fun selectArea(area: ZooArea) {
        selectedArea.value = area
    }

    fun selectPlant(plant: ZooPlant) {
        selectedPlant.value = plant
    }

    fun selectAnimal(animal: ZooAnimal) {
        selectedAnimal.value = animal
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

    private suspend fun fetchPlantsInfo(plantsQuery: String) {
        try {
            val plantsMeta = getNewMeta(plantsQuery, Constants.KEY_SP_ZOO_PLANTS_DATE_TIME)
            plantsMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                zooRepo.fetchPlants(plantsRid)
            }
        } catch (e: Exception) {
            Log.d(tagName, e.message , e)
        }
    }

    private suspend fun fetchAnimalsInfo(animalsQuery: String) {
        try {
            val animalMeta = getNewMeta(animalsQuery, Constants.KEY_SP_ZOO_ANIMALS_DATE_TIME)
            animalMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                zooRepo.fetchAnimals(plantsRid)
            }
        } catch (e: Exception) {
            Log.d(tagName, e.message , e)
        }
    }
}