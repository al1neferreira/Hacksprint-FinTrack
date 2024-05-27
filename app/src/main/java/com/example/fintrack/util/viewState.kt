package com.example.fintrack.util

import androidx.constraintlayout.motion.utils.ViewState
import com.example.fintrack.model.Transaction

class viewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val transaction: List<Transaction>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()

}