package com.ticketbook.movieticketbookingapp.ticketbooking

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val rightSpace: Int): RecyclerView.ItemDecoration()  {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            right = rightSpace
        }
    }
}