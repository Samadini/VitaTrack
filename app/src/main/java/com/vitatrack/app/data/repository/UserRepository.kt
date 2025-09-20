package com.vitatrack.app.data.repository

import androidx.lifecycle.LiveData
import com.vitatrack.app.data.dao.UserDao
import com.vitatrack.app.data.model.User

class UserRepository(private val userDao: UserDao) {
    
    suspend fun getUserById(uid: String): User? {
        return userDao.getUserById(uid)
    }
    
    fun getUserByIdLiveData(uid: String): LiveData<User?> {
        return userDao.getUserByIdLiveData(uid)
    }
    
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
    
    suspend fun deleteUserById(uid: String) {
        userDao.deleteUserById(uid)
    }
}
