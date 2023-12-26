package com.ticketbook.movieticketbookingapp.ticketbooking.datamodel

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.SeatModel

data class SeatRowModel(val rowId: String, val rowName: String, val rowOrder: Int, val seatList: List<SeatModel>)
