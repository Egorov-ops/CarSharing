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

    private val cars: MutableList<CarEntity> = mutableListOf(
        CarEntity(
            1,
            "Toyota Altezza",
            TypeOfCar.NO_TRUCK,
            region = "Asia/Japan",
            state = StateOfCar.ON_REPAIR,
            image = "https://a.d-cd.net/JIiHMbeYNwER5aQRQ-zt-9A1QLI-1920.jpg",
            description = "Toyota Altezza - компактный заднеприводный автомобиль с элегантным дизайном, спортивной подвеской и надежным двигателем."
        ),
        CarEntity(
            2,
            "Audi A6",
            TypeOfCar.NO_TRUCK,
            region = "Europe/Germany",
            state = StateOfCar.IN_FLIGHT,
            image = "https://s0.rbk.ru/v6_top_pics/resized/1440xH/media/img/6/06/755773633153066.jpg",
            description = "Audi A6 - престижный и комфортабельный автомобиль бизнес-класса с высоким уровнем отделки, технологичными системами и мощными двигателями."
        ),
        CarEntity(
            3,
            "Hyndai Smth",
            TypeOfCar.TRUCK,
            region = "Asia/Yekaterinburg",
            state = StateOfCar.SOLD,
            image = "https://www.autostat.ru/application/includes/blocks/big_photo/images/cache/000/098/048/22bd1f69-670-0.jpg?_=1637233323",
            description = "Грузовики от Hyundai - надежные и эффективные транспортные средства, предлагающие просторные кабины, современные технологии и различные варианты грузоподъемности."
        ),
        CarEntity(
            4,
            "Lada Vesta",
            TypeOfCar.NO_TRUCK,
            region = "Asia/Chelyabinsk",
            state = StateOfCar.ON_REPAIR,
            image = "https://avtovesti.com/wp-content/uploads/2017/08/lada_vesta_40.jpeg",
            description = "Lada Vesta - надежный и доступный автомобиль, предлагающий комфортабельный салон, экономичный двигатель и хорошую управляемость."
        ),
        CarEntity(
            5,
            "Subaru Impreza",
            TypeOfCar.NO_TRUCK,
            region = "Europe/Moscow",
            state = StateOfCar.IN_FLIGHT,
            image = "https://all-auto.org/wp-content/uploads/2020/10/Subaru-WRX-2-1.jpg",
            description = "Subaru Impreza - спортивный и надежный автомобиль, известный своим высоким уровнем проходимости и управляемости. Он оснащен надежным полным приводом Symmetrical All-Wheel Drive, мощным двигателем и превосходной динамикой. Внутри просторный и комфортабельный салон с современными технологиями и высоким уровнем безопасности. Subaru Impreza - идеальный выбор для любителей адреналина и путешествий."
        ),
    )

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