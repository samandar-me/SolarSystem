package com.sdk.planetnew.domain.main

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class HomeRepository {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    fun queryPlanets(): Task<QuerySnapshot> {
        return firebaseFirestore.collection("planets").get()
    }
}