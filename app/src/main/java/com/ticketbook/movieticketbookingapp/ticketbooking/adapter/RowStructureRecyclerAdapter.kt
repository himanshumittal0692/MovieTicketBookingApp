package com.ticketbook.movieticketbookingapp.ticketbooking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ticketbook.movieticketbookingapp.R
import com.ticketbook.movieticketbookingapp.databinding.ItemScreenRowBinding
import com.ticketbook.movieticketbookingapp.ticketbooking.MarginItemDecoration
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.RowSeatReferenceModel
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.SeatRowModel

class RowStructureRecyclerAdapter(
    val map: HashMap<RowSeatReferenceModel, Boolean>,
    private val onSeatTapped: ((seat: RowSeatReferenceModel, isChecked: Boolean) -> Unit)
) : ListAdapter<SeatRowModel, RowStructureRecyclerAdapter.RowStructureViewHolder>(DIFF_CALLBACK) {

    inner class RowStructureViewHolder(private val binding: ItemScreenRowBinding) :
        ViewHolder(binding.root) {
        fun bind(position: Int) {
            val model = getItem(position)
            binding.tvRowName.text = model.rowName
            setSeatListRecyclerAdapter(model)
        }

        private fun setSeatListRecyclerAdapter(model: SeatRowModel) {
            val seatAdapter = SeatStructureRecyclerAdapter(model.rowId, map, onSeatTapped = { seat: RowSeatReferenceModel, isChecked: Boolean ->
                onSeatTapped.invoke(seat, isChecked)
            })
            binding.rvSeatStructure.apply {
               layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = seatAdapter
            }
            seatAdapter.submitList(model.seatList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowStructureViewHolder {
        val binding =
            ItemScreenRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RowStructureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowStructureViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeatRowModel>() {

            //Comparing whole objects like oldItem == newItem will not work here because,
            //memory addresses will be different once response has been updated
            //even if content is exactly same.
            override fun areItemsTheSame(oldItem: SeatRowModel, newItem: SeatRowModel) =
                oldItem.rowId == newItem.rowId

            override fun areContentsTheSame(oldItem: SeatRowModel, newItem: SeatRowModel) =
                oldItem == newItem
        }
    }


}