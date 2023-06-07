package com.example.rentcars.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.ProfileEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await



suspend fun getCars(profileEntity: ProfileEntity, carEntity: CarEntity): List<CarEntity> {
    val db = Firebase.firestore
    val userId = profileEntity.id.toString()

    val carList = mutableListOf<CarEntity>()

    val querySnapshot = db.collection("Cars")
        .whereEqualTo("userId", userId)
        .get()
        .await() // Добавляем await(), чтобы дождаться завершения операции получения данных

    for (documentSnapshot in querySnapshot.documents) {
        // Получите данные машины из документа
        val carData = documentSnapshot.data

        // Создайте объект Car и добавьте его в список
        val car = carEntity

        // Добавьте другие поля машины по необходимости

        carList.add(carEntity)
    }

    return carList
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

    fun editStateCar(carEntity: CarEntity, newState: CarEntity) {
        val db = Firebase.firestore
        db.collection("Cars").document(carEntity.state.toString())
            .update(carEntity.state.toString(), newState)
    }

    fun editProfile(profileEntity: ProfileEntity, newName: String) {
        /* Видимо с binding тягать данные будешь я хз как параметры их передавать , в комментах чтобы ошибок не было,
         там допиши параметры передаваемые
        потому что я хз что туда передать , написал как пример
        */
        val db = Firebase.firestore
        db.collection("Users").document(profileEntity.name).update(profileEntity.name, newName)
        //db.collection("Users").document(profileEntity.phone).update(profileEntity.phone)
        //db.collection("Users").document(profileEntity.region).update(profileEntity.region)


    }

    fun createUser(email:String,password:String,name:String,phone:String,region:String){
        val db = Firebase.firestore
        val mAuth = Firebase.auth
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser

                    val userForDb = user?.uid?.let { it2 ->
                        ProfileEntity(
                            it2,
                            name,
                            phone,
                            region
                        )
                    }


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)


                }
            }
    }





/*
@InstallIn(Singleton::class)
@Module
object firebaseModule(){
    @Provides
    @Singleton
    fun provideFirestoreInstance():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
}

*/


