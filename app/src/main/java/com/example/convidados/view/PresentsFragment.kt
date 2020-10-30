package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.service.model.GuestModel
import com.example.convidados.view.adapter.GuestAdapter
import com.example.convidados.view.listener.GuestListener
import com.example.convidados.viewmodel.AllGuestsViewModel
import com.example.convidados.viewmodel.GuestFormViewModel

class PresentsFragment : Fragment() {

    private lateinit var mAllGuestsViewModel: AllGuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter: GuestAdapter = GuestAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mAllGuestsViewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_present, container, false)

        // RECYCLER SETUP
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_presents)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mAllGuestsViewModel.delete(id)
                mAllGuestsViewModel.load(GuestConstants.FILTER.PRESENTS)
            }

        }

        mAdapter.attachListener(mListener)

        observer()

        return root
    }


    override fun onResume() {
        super.onResume()
        mAllGuestsViewModel.load(GuestConstants.FILTER.PRESENTS)
    }

    private fun observer() {
        mAllGuestsViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }
}