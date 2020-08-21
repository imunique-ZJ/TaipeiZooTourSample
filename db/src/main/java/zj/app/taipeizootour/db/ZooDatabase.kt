package zj.app.taipeizootour.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zj.app.taipeizootour.db.dao.*
import zj.app.taipeizootour.db.model.*

@Database(entities = [ZooArea::class,
    ZooPlant::class,
    ZooAnimal::class,
    AreaPlantsCrossRef::class,
    AreaAnimalsCrossRef::class], version = 1)
abstract class ZooDatabase: RoomDatabase() {

    abstract fun zooAreaDao(): ZooAreaDao
    abstract fun zooPlantDao(): ZooPlantDao
    abstract fun zooAnimalDao(): ZooAnimalDao
    abstract fun areaPlantsDao(): AreaPlantsDao
    abstract fun areaAnimalsDao(): AreaAnimalsDao

    companion object {
        @Volatile
        private var INSTANCE: ZooDatabase? = null
        private const val dbName = "zoo_db"

        fun getDatabase(context: Context): ZooDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZooDatabase::class.java,
                    dbName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}