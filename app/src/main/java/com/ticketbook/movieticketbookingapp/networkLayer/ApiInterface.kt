package com.ticketbook.app.networkLayer

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v3/b1106bbd-372d-4b04-931e-082cb95822cb")
    suspend fun getTicketRawStructure(): Response<TicketScreenModel>
}