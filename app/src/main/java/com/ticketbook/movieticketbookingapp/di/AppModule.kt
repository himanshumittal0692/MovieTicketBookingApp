package com.ticketbook.movieticketbookingapp.di

import com.ticketbook.movieticketbookingapp.ticketbooking.datasource.IDataSource
import com.ticketbook.movieticketbookingapp.ticketbooking.datasource.NetworkDataSourceImpl
import com.ticketbook.movieticketbookingapp.ticketbooking.repository.ISeatSelectionRepository
import com.ticketbook.movieticketbookingapp.ticketbooking.repository.SeatSelectionRepositoryImpl
import com.ticketbook.movieticketbookingapp.ticketbooking.usecase.ISeatSelectionUseCase
import com.ticketbook.movieticketbookingapp.ticketbooking.usecase.SeatSelectionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    internal abstract fun bindSeatSelectionUseCase(
        seatSelectionUseCase: SeatSelectionUseCaseImpl
    ): ISeatSelectionUseCase

    @Binds
    internal abstract fun bindSeatSelectionRepository(
        seatSelectionRepository: SeatSelectionRepositoryImpl
    ): ISeatSelectionRepository

    @Binds
    internal abstract fun bindNetworkDataSource(
        networkDataSourceImpl: NetworkDataSourceImpl
    ): IDataSource
}