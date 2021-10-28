package com.example.legalqa

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val mLawyerQueryList = MutableLiveData<LawyerQuery>()
}