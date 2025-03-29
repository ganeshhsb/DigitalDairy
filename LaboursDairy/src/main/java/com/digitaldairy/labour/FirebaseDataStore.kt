package com.digitaldairy.labour

import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.WorkDetail
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataStore(val firestore: FirebaseFirestore) {

    fun insertPerson(person: Person) {
        addLabour(person)
    }

    fun insertWorkDetail(uid:String, workDetail: WorkDetail) {
        addWorkDetail(uid, workDetail)
    }

    private fun addLabour(person: Person) {
        val peopleFB = hashMapOf(
            "uid" to person.personId,
            "first_name" to person.firstName,
            "last_name" to person.lastName,
            "sex" to true, //people.sex,
            "age" to person.age
        )

        firestore.collection("DigitalDairy").document("Person")
            .set(peopleFB)
            .addOnSuccessListener { println("Data added!") }
            .addOnFailureListener { e -> println("Error: $e") }
    }

    private fun addWorkDetail(personId:String, workDetail: WorkDetail) {
        val workDetailFB = hashMapOf(
            "uid" to workDetail.personId,
            "amount_paid" to workDetail.amountPaid,
//            "daily_wage" to workDetail.,
            "date" to workDetail.date,
            "hours" to workDetail.hours,
//            "is_paid" to workDetail.isPaid,
            "work_description" to workDetail.workDescription,
        )

        firestore.collection("DigitalDairy").document("Person")
            .collection(personId).document("work_detail")
            .set(workDetailFB)
            .addOnSuccessListener { println("Data added!") }
            .addOnFailureListener { e -> println("Error: $e") }
    }

    fun getLabour() {
        firestore.collection("DigitalDairy").document("Person")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firstName = document.getString("first_name")
                    val lastName = document.getLong("last_name")
                    val sex = document.getString("sex")
                    val age = document.getString("age")
                }
            }
            .addOnFailureListener { e -> println("Error: $e") }
    }

}