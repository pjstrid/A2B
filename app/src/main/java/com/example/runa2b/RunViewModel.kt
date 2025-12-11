package com.example.runa2b

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class RunViewModel : ViewModel() {


    private val dataManager = FirebaseManager()

    val run: LiveData<MutableList<Run>> = dataManager.runs



    fun addRun(distance: Double, name: String, date: String) {
        dataManager.addRun(distance, name, date)
    }

    fun updateRun(id: String, distance: Double, name: String, date: String) {
        dataManager.updateRun(id, distance, name, date)
    }

    fun getRun(id: String): Run? {
        return dataManager.getRun(id)
    }

    fun deleteRun(id: String) {
        dataManager.deleteRun(id)
    }
}