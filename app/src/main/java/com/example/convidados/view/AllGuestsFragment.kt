package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.view.adapter.GuestAdapter
import com.example.convidados.view.listener.GuestListener
import com.example.convidados.viewmodel.AllGuestsViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mAllGuestsViewModel: AllGuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter: GuestAdapter = GuestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAllGuestsViewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_all, container, false)

        // RECYCLER VIEW Phases
        // GET -> Refer the Recycler Element through ROOT
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_guests)

        // DEFINE -> Set the layout manager to the recycler element
        recycler.layoutManager = LinearLayoutManager(context)

        // DEFINE ADAPTER -> Set the Adapter(+ viewHolder) to the recycler element
        recycler.adapter = mAdapter

        // Attaching the OnClickListener
        mListener = object : GuestListener {
            override fun onClick(id: Int) {

                // Passing guestID through bundle to the Intent
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mAllGuestsViewModel.delete(id)
                mAllGuestsViewModel.load(GuestConstants.FILTER.ALL)
            }
        }

        mAdapter.attachListener(mListener)

        observer()

        return root
    }

    // Reload the list when the fragment comeback to be in evidence NOT ONLY on create
    override fun onResume() {
        super.onResume()
        mAllGuestsViewModel.load(GuestConstants.FILTER.ALL)
    }

    // SETUP the Observers of the activity
    private fun observer() {
        mAllGuestsViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }
}