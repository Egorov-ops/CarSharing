package com.example.rentcars.data.repository.impl

import android.content.ContentValues
import android.util.Log
import com.example.rentcars.data.UserDataCredentials
import com.example.rentcars.data.entity.CarEntity
import com.example.rentcars.data.entity.StateOfCar
import com.example.rentcars.data.entity.TypeOfCar
import com.example.rentcars.data.repository.CarsRepository
import com.example.rentcars.utils.ResultWrapper
import com.example.rentcars.utils.safeApiCall
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userDataCredentials: UserDataCredentials
) : CarsRepository {



    override suspend fun getCars(): ResultWrapper<List<CarEntity>> {
        return safeApiCall(Dispatchers.IO) {
            val userId = userDataCredentials.getToken().toString()

            val carList = mutableListOf<CarEntity>()

            val querySnapshot = firestore.collection("Cars")
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

            carList
        }
    }

//    override suspend fun getCar(id: Int): ResultWrapper<CarEntity> {
//        return safeApiCall(Dispatchers.IO) {
//            cars.find { carEntity -> carEntity.id == id } ?: cars.first()
//        }
//    }

    override fun addCar(
        carEntity: CarEntity
    ) {
        firestore.collection("Cars").document(carEntity.markAndModel).set(carEntity)
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    override fun deleteCar(carEntity: CarEntity) {
        firestore.collection("Cars").document(carEntity.markAndModel).delete()
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully deleted!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
    }

    override fun changeStateCar(
        oldStateOfCar: StateOfCar,
        newStateOfCar: StateOfCar
    ) {
        firestore.collection("Cars").document(oldStateOfCar.title)
            .update(oldStateOfCar.title, newStateOfCar.title)

    }

}