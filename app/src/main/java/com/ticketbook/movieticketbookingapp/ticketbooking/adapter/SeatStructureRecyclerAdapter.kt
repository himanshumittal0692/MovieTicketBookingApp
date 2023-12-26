package com.ticketbook.movieticketbookingapp.ticketbooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ticketbook.movieticketbookingapp.R
import com.ticketbook.movieticketbookingapp.databinding.ItemSeatStructureBinding
import com.ticketbook.movieticketbookingapp.ticketbooking.SeatAvailabilityStatus
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.RowSeatReferenceModel
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.SeatModel


class SeatStructureRecyclerAdapter(
    private val rowId: String, val map: HashMap<RowSeatReferenceModel, Boolean>,
    private val onSeatTapped: ((seat: RowSeatReferenceModel, isChecked: Boolean) -> Unit)
) : ListAdapter<SeatModel, SeatStructureRecyclerAdapter.SeatStructureViewHolder>(DIFF_CALLBACK) {

    inner class SeatStructureViewHolder(private val binding: ItemSeatStructureBinding) :
        ViewHolder(binding.root) {
        fun bind(position: Int) {
            val model = getItem(position)
            binding.root.isVisible = model.visibility
            binding.tvSeat.text = "${model.seatNumber}"
            setSeatOccupancy(model)
            handleSeatTap(model)
        }

        private fun handleSeatTap(model: SeatModel) {
            binding.root.setOnClickListener {
                if (SeatAvailabilityStatus.OCCUPIED == SeatAvailabilityStatus.valueOf(model.seatState)) {
                    showSeatOccupiedToast()
                    return@setOnClickListener
                }
                handleSeatSelection(model)
            }
        }

        private fun setSeatOccupancy(model: SeatModel) {
            val rowSeatReferenceModel = RowSeatReferenceModel(rowId, model.seatId)
            binding.tvSeat.apply {
                if (true == map[rowSeatReferenceModel]) {
                    context.resources.getDrawable(R.drawable.rectancular_selected, null)
                } else if (SeatAvailabilityStatus.AVAILABLE == SeatAvailabilityStatus.valueOf(model.seatState)) {
                    background =
                        binding.tvSeat.context.resources.getDrawable(
                            R.drawable.rectangular_available,
                            null
                        )
                } else if (SeatAvailabilityStatus.OCCUPIED == SeatAvailabilityStatus.valueOf(model.seatState)) {
                    background =
                        context.resources.getDrawable(R.drawable.rectangular_filled, null)
                }
            }

        }

        private fun handleSeatSelection(model: SeatModel) {
            val rowSeatReferenceModel = RowSeatReferenceModel(rowId = rowId, model.seatId)
            if (true == map[rowSeatReferenceModel]) {
                binding.tvSeat.background =
                    binding.tvSeat.context.resources.getDrawable(R.drawable.rectangular_available, null)
                onSeatTapped.invoke(rowSeatReferenceModel, false)
                return
            }

            val rowSeatReferenceList = ArrayList<RowSeatReferenceModel>()
            map.forEach { (key, value) ->
                if (value) rowSeatReferenceList.add(key)
            }

            binding.tvSeat.background =
                binding.tvSeat.context.resources.getDrawable(R.drawable.rectancular_selected, null)
            onSeatTapped.invoke(rowSeatReferenceModel, true)
            if (rowSeatReferenceList.size >= 2) {
                onSeatTapped.invoke(rowSeatReferenceModel, true)
                unselectPreviousSeatSelection(rowSeatReferenceList)
            }
        }

        /*
  If Already two seats are selected and user try to add the new seat,
  the previous selected seats will be removed and new seat be will selected.
   */
        private fun unselectPreviousSeatSelection(rowSeatReferenceList: ArrayList<RowSeatReferenceModel>) {
            rowSeatReferenceList.forEach {
                onSeatTapped.invoke(it, false)
            }
        }

        private fun showSeatOccupiedToast() {
            binding.root.context.apply {
                Toast.makeText(this, getString(R.string.seat_already_occupied), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatStructureViewHolder {
        val binding =
            ItemSeatStructureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SeatStructureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatStructureViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeatModel>() {

            //Comparing whole objects like oldItem == newItem will not work here because,
            //memory addresses will be different once response has been updated
            //even if content is exactly same.
            override fun areItemsTheSame(oldItem: SeatModel, newItem: SeatModel) =
                oldItem.seatId == newItem.seatId

            override fun areContentsTheSame(oldItem: SeatModel, newItem: SeatModel) =
                oldItem == newItem
        }
    }
}