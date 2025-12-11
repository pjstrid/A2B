package com.example.runa2b

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class RunViewModel : ViewModel() {


    private val dataManager = FirebaseManager()

    val employee: LiveData<MutableList<Run>> = dataManager.runs



    fun addEmployee(name: String, age: Int, salary: Double) {
        dataManager.addEmployee(name, age, salary)
    }

    fun updateEmployee(id: String, name: String, age: Int, salary: Double) {
        dataManager.updateEmployee(id, name, age, salary)
    }

    fun getEmployee(id: String): Run? {
        return dataManager.getEmployee(id)
    }

    fun deleteEmployee(id: String) {
        dataManager.deleteEmployee(id)
    }
}