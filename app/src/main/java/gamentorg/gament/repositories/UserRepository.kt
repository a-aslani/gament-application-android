package gamentorg.gament.repositories

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.UserDao
import gamentorg.gament.db.entities.User
import gamentorg.gament.services.network.ApiService
import gamentorg.gament.services.network.models.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(appDatabase: AppDatabase, private val apiService: ApiService) {

    private val userDao: UserDao = appDatabase.userDao()

    companion object {

        internal class InsertUser(private val dao: UserDao): AsyncTask<User, Void?, Void?>() {
            override fun doInBackground(vararg params: User?): Void? {
                dao.insertUser(params[0]!!)
                return null
            }
        }

        internal class DeleteUser(private val dao: UserDao): AsyncTask<Void, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                dao.deleteAllUser()
                return null
            }
        }
    }

    fun deleteUser() {
        DeleteUser(userDao).execute()
    }

    fun insertUser(apiToken: String) {
        apiService.fetchUserInfo(apiToken).enqueue(object : Callback<UserInfoResponse> {
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.e("network error", t.message)
            }

            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.state) {
                        val user = body.data.document
                        InsertUser(userDao).execute(user)
                    }
                }
            }
        })
    }

    fun getUser(): LiveData<User> {
        return userDao.getUser()
    }
}