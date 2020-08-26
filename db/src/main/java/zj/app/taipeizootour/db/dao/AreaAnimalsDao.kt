package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import zj.app.taipeizootour.db.data.AreaWithAnimals
import zj.app.taipeizootour.db.model.AreaAnimalsCrossRef

@Dao
interface AreaAnimalsDao {

    @Transaction
    @Query("SELECT * FROM ZooArea")
    fun queryAll(): LiveData<List<AreaWithAnimals>>

    @Transaction
    @Query("SELECT * FROM ZooArea WHERE areaId = :areaId")
    fun queryByAreaLive(areaId: Int): LiveData<AreaWithAnimals?>

    @Transaction
    @Query("SELECT * FROM ZooArea WHERE areaId = :areaId")
    suspend fun queryByArea(areaId: Int): AreaWithAnimals?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joins: List<AreaAnimalsCrossRef>)
}