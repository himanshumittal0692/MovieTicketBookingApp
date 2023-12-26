package com.ticketbook.movieticketbookingapp.ticketbooking.datamodel

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.SeatRowModel

data class TicketScreenModel(val screenId: String, val screenName: String, val rowList: List<SeatRowModel>)
