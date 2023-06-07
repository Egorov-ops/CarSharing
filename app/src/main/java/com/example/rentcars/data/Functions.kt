package com.example.rentcars.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.ProfileEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


suspend fun getCars(profileId: Int): List<CarEntity> {
    val db = Firebase.firestore
    val userId = profileId.toString()

    val carList = mutableListOf<CarEntity>()

    val querySnapshot = db.collection("Cars")
        .whereEqualTo("userId", userId)
        .get()
        .await() // Добавляем await(), чтобы дождаться завершения операции получения данных

    for (documentSnapshot in querySnapshot.documents) {
// Получите данные машины из документа
       // val carData = documentSnapshot.data

        // Преобразуйте carData в объект CarEntity
        val carEntity = documentSnapshot.toObject(CarEntity::class.java)

        // Добавьте carEntity в список carList
        carEntity?.let {
            carList.add(it)
        }

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

fun getUserInfo(token: String, callback: (ProfileEntity?) -> Unit) {
    val db = Firebase.firestore
    val userRef = db.collection("Users").document(token)
    userRef.get().addOnSuccessListener { document ->
        if (document != null && document.exists()) {
            // Документ существует, получите данные пользователя
            val userData = document.data

            // Обработайте данные пользователя по вашему усмотрению
            val name = userData?.get("name") as String
            val phone = userData?.get("phone") as String
            val region = userData?.get("region") as String

            val userInfo = ProfileEntity(token, name, phone, region)
            callback(userInfo)
        } else {
            callback(null) // Возвращаем null в случае, если документ не существует
        }
    }.addOnFailureListener { exception ->
        // Обработка ошибок при получении данных
        callback(null) // Возвращаем null в случае ошибки
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
