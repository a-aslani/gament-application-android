package gamentorg.gament.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gamentorg.gament.db.dao.GameDao
import gamentorg.gament.db.dao.TournamentDao
import gamentorg.gament.db.dao.UserDao
import gamentorg.gament.db.entities.Game
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.db.entities.User

@Database(entities = [Game::class, User::class, Tournament::class], version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var sInstance : AppDatabase? = null

        fun appDatabase(application: Application): AppDatabase {

            if (sInstance == null) {

                synchronized(RoomDatabase::class.java) {

                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "gament_db").fallbackToDestructiveMigration().build()
                    }
                }
            }

            return sInstance!!
        }
    }

    abstract fun gameDao(): GameDao
    abstract fun userDao(): UserDao
    abstract fun tournamentDao(): TournamentDao
}