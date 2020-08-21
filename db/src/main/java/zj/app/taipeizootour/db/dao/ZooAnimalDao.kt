package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zj.app.taipeizootour.db.model.ZooAnimal

@Dao
interface ZooAnimalDao {

    @Query("SELECT * FROM ZooAnimal ORDER BY animalId ASC")
    fun queryAll(): LiveData<List<ZooAnimal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zooAnimals: List<ZooAnimal>)

    @Query("DELETE FROM ZooAnimal")
    suspend fun deleteAll()
}