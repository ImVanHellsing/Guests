package com.example.convidados.service.constants

class GuestConstants private constructor() {
    companion object {
        const val GUESTID = "guestID"

    }

    object FILTER {
        const val ALL = 1
        const val PRESENTS = 2
        const val ABSENTS = 3
    }
}