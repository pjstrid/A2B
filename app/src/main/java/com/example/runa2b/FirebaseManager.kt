package com.example.runa2b

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class FirebaseManager {

    private val db = Firebase.firestore

    private val _runs = MutableLiveData(mutableListOf<Run>())

    val runs: LiveData<MutableList<Run>> get() = _runs

    lateinit var currentUser: FirebaseUser

    init {
        addSnapshotListener()
    }

    fun addSnapshotListener() {

        currentUser = Firebase.auth.currentUser ?: return

        db.collection("users").document(currentUser.uid)
            .collection("runs").addSnapshotListener { snapshot, error ->

                if (snapshot != null) {
                    val tempList = mutableListOf<Run>()

                    for (doc in snapshot.documents) {
                        val employee = doc.toObject(Run::class.java)!!.copy(id = doc.id)

                        tempList.add(employee)
                    }
                    _runs.value = tempList
                }

            }
    }

    fun addEmployee(distance: Double, name: String, date: String) {
        currentUser = Firebase.auth.currentUser ?: return

        val fields = mapOf(
            "distance" to distance,
            "name" to name,
            "date" to date
        )

        db.collection("users").document(currentUser.uid)
            .collection("runs").add(fields).addOnSuccessListener { documentReference ->
                Log.i("SOUT", "Added run to database with id: " + documentReference.id)
            }.addOnFailureListener {
                Log.e("SOUT", "Failed to add run to database, error: " + it.message)
            }

    }

    fun getEmployee(id: String): Run? {

        return runs.value?.find { it.id == id }
    }

    fun updateEmployee(id: String, distance: Double, name: String, date: String) {

        currentUser = Firebase.auth.currentUser ?: return

        val fields = mapOf(
            "distance" to distance,
            "name" to name,
            "date" to date
        )

        db.collection("users").document(currentUser.uid)
            .collection("employees").document(id).update(fields)
            .addOnSuccessListener { documentReference ->
                Log.i("SOUT", "Updated run on database with id: $id")
            }.addOnFailureListener {
                Log.e("SOUT", "Failed to update run on database, error: " + it.message)
            }
    }

    fun deleteEmployee(id: String) {

        currentUser = Firebase.auth.currentUser ?: return

        db.collection("users").document(currentUser.uid)
            .collection("employees").document(id).delete()
            .addOnSuccessListener { documentReference ->
                Log.i("SOUT", "Deleted run to database with id: $id")
            }.addOnFailureListener {
                Log.e("SOUT", "Failed to delete run from database, error: " + it.message)
            }
    }
}