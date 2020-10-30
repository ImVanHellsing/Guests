package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.viewmodel.GuestFormViewModel
import com.example.convidados.R
import kotlinx.android.synthetic.main.activity_guest_form.*
import java.lang.Exception

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    //My ViewModel LATEINIT
    private lateinit var mGuestFormViewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        //My ViewModel Initialization
        mGuestFormViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observer()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_save) {
            val name = edit_text_name.text.toString()
            val presence = rad_btn_present.isChecked

            mGuestFormViewModel.save(name, presence)
            /*mGuestFormViewModel.save(edit_text_name.text.toString(), rad_btn_present.isChecked)*/
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
    }

    private fun setListeners() {
        btn_save.setOnClickListener(this)
    }
}