package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.AreaPlantsCrossRef

@Dao
interface AreaPlantsDao {

    @Query("SELECT * FROM ZooArea")
    fun queryAll(): LiveData<List<AreaWithPlants>>

    @Query("SELECT * FROM ZooArea WHERE areaId = :areaId")
    fun queryByArea(areaId: Int): LiveData<List<AreaWithPlants>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joins: List<AreaPlantsCrossRef>)
}