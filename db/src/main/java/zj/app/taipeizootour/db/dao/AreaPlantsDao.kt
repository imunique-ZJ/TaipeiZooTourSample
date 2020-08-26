package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.AreaPlantsCrossRef

@Dao
interface AreaPlantsDao {

    @Transaction
    @Query("SELECT * FROM ZooArea")
    fun queryAll(): LiveData<List<AreaWithPlants>>

    @Transaction
    @Query("SELECT * FROM ZooArea WHERE areaId = :areaId")
    fun queryByAreaLive(areaId: Int): LiveData<AreaWithPlants?>

    @Transaction
    @Query("SELECT * FROM ZooArea WHERE areaId = :areaId")
    suspend fun queryByArea(areaId: Int): AreaWithPlants?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joins: List<AreaPlantsCrossRef>)
}