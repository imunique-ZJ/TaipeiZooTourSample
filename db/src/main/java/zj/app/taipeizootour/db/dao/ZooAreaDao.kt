package zj.app.taipeizootour.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zj.app.taipeizootour.db.model.ZooArea

@Dao
interface ZooAreaDao {
    @Query("SELECT * FROM ZooArea ORDER BY id ASC")
    fun queryAll(): LiveData<List<ZooArea>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zooAreas: List<ZooArea>)

    @Query("DELETE FROM ZooArea")
    suspend fun deleteAll()
}