package com.example.rentcars.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.data.entity.StateOfCar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun getCars(): List<CarEntity> {
    val db = Firebase.firestore

    return emptyList()
}

fun addCars(carEntity: CarEntity) {
    val db = Firebase.firestore
    db.collection("Cars").document(carEntity.markAndModel).set(carEntity)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

}

fun deleteCar(carEntity: CarEntity) {
    val db = Firebase.firestore
    db.collection("Cars").document(carEntity.markAndModel).delete()
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
}

fun editStateCar(carEntity: CarEntity,newState :CarEntity) {
    val db = Firebase.firestore
    db.collection("Cars").document(carEntity.state.toString()).update(carEntity.state.toString(),newState)
}

fun editProfile(profileEntity: ProfileEntity,newName:String) {
    /* Видимо с binding тягать данные будешь я хз как параметры их передавать , в комментах чтобы ошибок не было,
     там допиши параметры передаваемые
    потому что я хз что туда передать , написал как пример
    */
    val db = Firebase.firestore
    db.collection("Users").document(profileEntity.name).update(profileEntity.name,newName)
    //db.collection("Users").document(profileEntity.phone).update(profileEntity.phone)
    //db.collection("Users").document(profileEntity.region).update(profileEntity.region)


}


/*




 */