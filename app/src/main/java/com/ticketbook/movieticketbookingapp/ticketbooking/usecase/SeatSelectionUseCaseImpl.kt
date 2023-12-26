package com.ticketbook.movieticketbookingapp.ticketbooking.usecase

import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import com.ticketbook.movieticketbookingapp.ticketbooking.repository.ISeatSelectionRepository
import retrofit2.Response
import javax.inject.Inject

class SeatSelectionUseCaseImpl @Inject constructor(private val repository: ISeatSelectionRepository):
    ISeatSelectionUseCase {

    override suspend fun getSeatStructure(): Response<TicketScreenModel> {
        return repository.getSeatStructure()
    }
}