package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.service.model.GuestModel
import com.example.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    //Declare and Initialize Repository
    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    private var mNewGuest = MutableLiveData<GuestModel>()
    val newGuest: LiveData<GuestModel> = mNewGuest

    fun save(id: Int, name: String, presence: Boolean) {
        val newGuest = GuestModel(id, name, presence)

        // Mutable data will be Observed returning (true, false) from DB
        if (id == 0) {
            mSaveGuest.value = mGuestRepository.save(newGuest)
        } else {
            mSaveGuest.value = mGuestRepository.update(newGuest)
        }

        // IF TERNARY
        /*mSaveGuest.value = (id == 0) ? mGuestRepository.save(newGuest) : mGuestRepository.update(newGuest)*/
    }

    fun load(id: Int) {

        // Mutable data will be Observed (GuestModel)
        mNewGuest.value = mGuestRepository.getOne(id)
    }
}