package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.service.model.GuestModel
import com.example.convidados.service.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    // Repository Instance
    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    // Mutable data setup
    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filterRequest: Int) {
        when (filterRequest) {
            GuestConstants.FILTER.ALL -> mGuestList.value = mGuestRepository.getAll()
            GuestConstants.FILTER.PRESENTS -> mGuestList.value = mGuestRepository.getPresents()
            GuestConstants.FILTER.ABSENTS -> mGuestList.value = mGuestRepository.getAbsents()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}