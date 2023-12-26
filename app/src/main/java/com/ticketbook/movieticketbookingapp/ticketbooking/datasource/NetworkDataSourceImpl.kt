package com.ticketbook.movieticketbookingapp.ticketbooking.datasource

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import com.ticketbook.app.networkLayer.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface):
    IDataSource {

    override suspend fun getSeatStructure(): Response<TicketScreenModel> {
        return apiInterface.getTicketRawStructure()
    }
}