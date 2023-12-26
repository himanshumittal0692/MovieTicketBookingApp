package com.ticketbook.movieticketbookingapp.ticketbooking.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.TicketScreenModel
import com.ticketbook.app.di.IoDispatcher
import com.ticketbook.app.networkLayer.NetworkResult
import com.ticketbook.movieticketbookingapp.ticketbooking.datamodel.SeatRowModel
import com.ticketbook.movieticketbookingapp.ticketbooking.usecase.ISeatSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatBookingViewModel @Inject constructor(private val seatSelectionUseCase: ISeatSelectionUseCase,
                                               @IoDispatcher private val dispatcher: CoroutineDispatcher): ViewModel() {

    private val _seatStructureResponse = MutableLiveData<NetworkResult<TicketScreenModel?>>()
    val seatStructureResponse: LiveData<NetworkResult<TicketScreenModel?>>
        get() = _seatStructureResponse

    fun getSeatStructure(){
        _seatStructureResponse.value = NetworkResult.Loading()
        viewModelScope.launch(dispatcher) {
           val response =  seatSelectionUseCase.getSeatStructure()
            if(response.isSuccessful && response.body() != null){
                _seatStructureResponse.postValue(NetworkResult.Success(response.body()))
            } else {
                _seatStructureResponse.postValue(NetworkResult.Error(response.message() ?: "", null))
            }
        }
    }

    fun getRowPositionFromRowId(rowList: List<SeatRowModel>, rowId: String): Int {
        rowList.forEachIndexed { index, seatRowModel ->
            if(rowId == seatRowModel.rowId){
                return index
            }
        }
        return -1
    }
}