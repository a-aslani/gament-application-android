package gamentorg.gament.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gamentorg.gament.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM `user` ORDER BY `id` DESC LIMIT 1")
    fun getUser(): LiveData<User>

    @Query("DELETE FROM user")
    fun deleteAllUser()
}