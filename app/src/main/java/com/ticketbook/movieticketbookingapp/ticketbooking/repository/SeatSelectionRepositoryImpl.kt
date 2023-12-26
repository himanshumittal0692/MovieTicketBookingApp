package com.ticketbook.movieticketbookingapp.ticketbooking.repository

import com.ticketbook.movieticketbookingapp.ticketbooking.datasource.IDataSource
import javax.inject.Inject

/**
 * This class the row and seat structure of the given screen
 * In constructor dataSource for Network and Cache can provided.
 * Currently having only Network DataSource, Cache DataSource can be added later as per requirement.
 * return retrofit.Response<TicketScreenModel>
 */
class SeatSelectionRepositoryImpl @Inject constructor(private val dataSource: IDataSource):
    ISeatSelectionRepository {

    override suspend fun getSeatStructure() = dataSource.getSeatStructure()

}