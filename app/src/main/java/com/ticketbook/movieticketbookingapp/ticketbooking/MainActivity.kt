package com.ticketbook.movieticketbookingapp.ticketbooking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.RowSeatReferenceModel
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import com.ticketbook.app.networkLayer.NetworkResult
import com.ticketbook.movieticketbookingapp.ticketbooking.adapter.RowStructureRecyclerAdapter
import com.ticketbook.movieticketbookingapp.ticketbooking.mvvm.SeatBookingViewModel
import com.ticketbook.movieticketbookingapp.R
import com.ticketbook.movieticketbookingapp.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var seatBookingViewModel: SeatBookingViewModel
    private val seatOccupancyMap = HashMap<RowSeatReferenceModel, Boolean>()
    private lateinit var adapter: RowStructureRecyclerAdapter
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        seatBookingViewModel = ViewModelProvider(this)[SeatBookingViewModel::class.java]
        binding = MainActivityBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        observeSeatStructureData()
        seatBookingViewModel.getSeatStructure()
        addTapListener()
    }

    private fun addTapListener() {
        binding.btProceed.setOnClickListener {
            if(seatOccupancyMap.size >= 2){
                Toast.makeText(this, getString(R.string.booking_can_be_proceed), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.min_seat_required), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeSeatStructureData() {
        seatBookingViewModel.seatStructureResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    showProgressBar()
                }

                is NetworkResult.Success<TicketScreenModel?> -> {
                    hideProgressBar()
                    binding.tvScreenDetails.text = it.data?.screenName
                    if (!this::adapter.isInitialized) {
                        initRowAdapter()
                    }
                    adapter.submitList(it.data?.rowList)
                }

                is NetworkResult.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        this,
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.layoutProgress.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.layoutProgress.visibility = View.GONE
    }

    private fun initRowAdapter() {
        adapter = RowStructureRecyclerAdapter(
            seatOccupancyMap,
            onSeatTapped = { seat: RowSeatReferenceModel, isChecked: Boolean ->
                handleSeatSelection(seat, isChecked)
            })
        binding.rvRowStructure.layoutManager = LinearLayoutManager(this)
        binding.rvRowStructure.adapter = adapter
    }

    /*
    This function calls when any non already occupied seat is tapped to select or unselect.
    If Seat is already selected user tap to unselect it, it will be removed from seatReference Map.
    If Seat is not already selected, than it will select and add in seatReference Map.
    If Already two seats are selected and user try to add the new seat,
    the previous selected seats will be removed and new seat be will selected and added in seatReference Map.
     */
    private fun handleSeatSelection(seat: RowSeatReferenceModel, checked: Boolean) {
        if (!checked && seatOccupancyMap.containsKey(seat)) {
            seatOccupancyMap.remove(seat)
            val rowPosition = seatBookingViewModel.getRowPositionFromRowId(adapter.currentList, seat.rowId)
            if(rowPosition >=0){
                adapter.notifyItemChanged(rowPosition)
            }
        } else if (checked) {
            seatOccupancyMap[seat] = true
        }

        if(seatOccupancyMap.size >= 2){
            binding.btProceed.background = resources.getDrawable(R.drawable.rectancular_selected, null)
        } else {
            binding.btProceed.background = resources.getDrawable(R.drawable.rectangular_filled, null)
        }
    }



}