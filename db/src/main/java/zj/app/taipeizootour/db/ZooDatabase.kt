package zj.app.taipeizootour.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zj.app.taipeizootour.db.dao.ZooAreaDao
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant

@Database(entities = [ZooArea::class, ZooPlant::class], version = 1)
abstract class ZooDatabase: RoomDatabase() {

    abstract fun zooAreaDao(): ZooAreaDao

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