package com.example.convidados.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.service.constants.DataBaseConstants
import com.example.convidados.service.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {

    // DB Instance
    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    // Turn into a Singleton object
    companion object {

        private lateinit var repository: GuestRepository

        fun getInstance(context: Context) : GuestRepository {

            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }

            return repository
        }
    }

    // CRUD = Create, Read, Update and Delete
    // Read
    fun getAll(): List<GuestModel> {
        val guestList: MutableList<GuestModel> = ArrayList()

        try {

            // Creating the QUERY ARG to the UPDATE
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            // DB FIND ALL result
            val cursor = mGuestDataBaseHelper.readableDatabase.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            // Verify DB result and MAP it
            if (cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    // Column values
                    val id_result =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name_result =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence_result =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    // Filling GUEST LIST with a new guest picked up from the resulted DB LIST
                    val current_guest = GuestModel(id_result, name_result, presence_result)
                    guestList.add(current_guest)
                }
            }

            // Disabling Cursor
            cursor?.close()

            guestList
        } catch (e: Exception) {
            guestList
        }

        return guestList
    }

    fun getPresents(): List<GuestModel> {
        val guestList: MutableList<GuestModel> = ArrayList()

        try {

            // CAREFUL!!!
            val cursor = mGuestDataBaseHelper.readableDatabase.rawQuery(
                "SELECT * FROM Guest WHERE presence = 1",
                null
            )

            // Verify DB result and MAP it
            if (cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    // Column values
                    val id_result =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name_result =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence_result =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    // Filling GUEST LIST with a new guest picked up from the resulted DB LIST
                    val current_guest = GuestModel(id_result, name_result, presence_result)
                    guestList.add(current_guest)
                }
            }

            // Disabling Cursor
            cursor?.close()

            guestList
        } catch (e: Exception) {
            guestList
        }

        return guestList
    }

    fun getAbsents(): List<GuestModel> {
        val guestList: MutableList<GuestModel> = ArrayList()

        try {

            // CAREFUL!!!
            val cursor = mGuestDataBaseHelper.readableDatabase.rawQuery(
                "SELECT * FROM Guest WHERE presence = 0",
                null
            )

            // Verify DB result and MAP it
            if (cursor != null && cursor.count > 0) {

                while (cursor.moveToNext()) {
                    // Column values
                    val id_result =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name_result =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence_result =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    // Filling GUEST LIST with a new guest picked up from the resulted DB LIST
                    val current_guest = GuestModel(id_result, name_result, presence_result)
                    guestList.add(current_guest)
                }
            }

            // Disabling Cursor
            cursor?.close()

            guestList
        } catch (e: Exception) {
            guestList
        }

        return guestList
    }

    fun getOne(id: Int): GuestModel? {

        var guest: GuestModel? = null

        return try {

            // RAW QUERY (Unsafe)
            /*val cursor = mGuestDataBaseHelper.readableDatabase.rawQuery("select name, presence from Guest where id = $id", null)*/

            // Creating the QUERY ARG to the UPDATE
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            // DB FIND ONE result
            val cursor = mGuestDataBaseHelper.readableDatabase.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            // Verify DB result
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                // Column values
                val name_result =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence_result =
                    (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name_result, presence_result)
            }

            // Disabling Cursor
            cursor?.close()

            guest
        } catch (e: Exception) {
            guest
        }
    }

    // Create
    fun save(guest: GuestModel): Boolean {

        return try {

            // writableDatabase
            val db = mGuestDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Update
    fun update(guestModel: GuestModel): Boolean {
        return try {

            // Creating the data values to UPDATE a data
            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guestModel.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guestModel.presence)

            // Creating the QUERY ARG to the UPDATE
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guestModel.id.toString())

            mGuestDataBaseHelper.writableDatabase.update(
                DataBaseConstants.GUEST.TABLE_NAME,
                contentValues,
                selection,
                args
            )
            true

        } catch (e: Exception) {
            false
        }
    }

    // Delete
    fun delete(guestId: Int): Boolean {

        return try {

            // Creating the QUERY ARG to DELETE
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guestId.toString())

            mGuestDataBaseHelper.writableDatabase.delete(
                DataBaseConstants.GUEST.TABLE_NAME,
                selection,
                args
            )
            true

        } catch (e: Exception) {
            false
        }
    }
}