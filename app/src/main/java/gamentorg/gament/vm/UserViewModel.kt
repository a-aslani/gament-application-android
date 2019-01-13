package gamentorg.gament.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import gamentorg.gament.db.entities.User
import gamentorg.gament.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    fun insertUser(apiToken: String) {
        repository.insertUser(apiToken)
    }

    val user: LiveData<User> = repository.getUser()

    fun deleteUser() {
        repository.deleteUser()
    }
}