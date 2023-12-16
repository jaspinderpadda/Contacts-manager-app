package com.jspadda.contactsmanager.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jspadda.contactsmanager.repository.UserRepository
import com.jspadda.contactsmanager.model.User
import kotlinx.coroutines.launch

class UserVM(private val repository: UserRepository): ViewModel(), Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllorDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllorDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){

        if(isUpdateOrDelete){
            // Make Update:
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)
        }else {

            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(User(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllorDelete() {
        if (isUpdateOrDelete) {
            delete(userToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun clearAll()= viewModelScope.launch {
        repository.deleteAll()
    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)

        // Resetting the Buttons and Fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllorDeleteButtonText.value = "Clear All"
    }
    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }

    fun initUpdateAndDelete(user: User){

        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearAllorDeleteButtonText.value = "Delete"

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}