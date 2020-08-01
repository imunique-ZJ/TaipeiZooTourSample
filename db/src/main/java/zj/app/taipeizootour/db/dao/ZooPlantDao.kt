package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zj.app.taipeizootour.db.model.ZooPlant

@Dao
interface ZooPlantDao {

    @Query("SELECT * FROM ZooPlant ORDER BY plantId ASC")
    fun queryAll(): LiveData<List<ZooPlant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zooPlants: List<ZooPlant>)

    @Query("DELETE FROM ZooPlant")
    suspend fun deleteAll()
}