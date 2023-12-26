package com.ticketbook.movieticketbookingapp.ticketbooking.datasource

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import retrofit2.Response

interface IDataSource {

    /**
     * This functions the row and seat structure of the given screen
     * return retrofit.Response<TicketScreenModel>
     */
    suspend fun getSeatStructure(): Response<TicketScreenModel>
}