package com.example.convidados.service.constants

class DataBaseConstants private constructor() {

    /**
     * Tables from database and their columns
     */

    object GUEST {
        const val TABLE_NAME = "GUEST"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}