package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.viewmodel.GuestFormViewModel
import com.example.convidados.R
import com.example.convidados.service.constants.GuestConstants
import kotlinx.android.synthetic.main.activity_guest_form.*
import java.lang.Exception

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    //My ViewModel LATEINIT
    private lateinit var mGuestFormViewModel: GuestFormViewModel
    private var mGuestID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        //My ViewModel Initialization
        mGuestFormViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        observer()
        setListeners()
        loadData()

        // Default value
        rad_btn_present.isChecked = true
    }

    // Called if the Activity has been Intented by OnClick (element) in the GuestListFragment
    private fun loadData() {
        val receivedBundle = intent.extras
        if (receivedBundle != null) {
            mGuestID = receivedBundle.getInt(GuestConstants.GUESTID)
            mGuestFormViewModel.load(mGuestID)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_save) {
            val name = edit_text_name.text.toString()
            val presence = rad_btn_present.isChecked

            // ??? RESPONSABILITY
            /*if (mGuestID == 0) mGuestFormViewModel.save(name, presence) else mGuestFormViewModel.update(name, presence)*/
            mGuestFormViewModel.save(mGuestID, name, presence)
        }
    }

    private fun observer() {
        mGuestFormViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha!", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mGuestFormViewModel.newGuest.observe(this, Observer {

            if (it == null) {
                Toast.makeText(this, "Falha no carregamento", Toast.LENGTH_SHORT)
            } else {
                edit_text_name.setText(it.name)
                if (it.presence) rad_btn_present.isChecked = true else rad_btn_absent.isChecked =
                    true
            }
        })
    }

    private fun setListeners() {
        btn_save.setOnClickListener(this)
    }
}