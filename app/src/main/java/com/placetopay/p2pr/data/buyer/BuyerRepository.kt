package com.placetopay.p2pr.data.buyer

import com.placetopay.p2pr.data.checkout.base.CheckoutAddress
import com.placetopay.p2pr.data.checkout.base.CheckoutBuyer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuyerRepository @Inject constructor() {

    fun getBuyerList(): List<BuyerPackage> = listOf(
        BuyerPackage(
            id = 1,
            title = "Buyer from Bogotá",
            buyer = CheckoutBuyer(
                CheckoutAddress(
                    "Bogotá",
                    "CO",
                    "+5712345678",
                    "110111",
                    "Cundinamarca",
                    "Carrera 123"
                ),
                "1234567890",
                "CC",
                "comprador1@placetopay.com",
                "+573001234567",
                "Juan",
                "Pérez"
            )
        ),
        BuyerPackage(
            id = 2,
            title = "Buyer from Medellín",
            buyer = CheckoutBuyer(
                CheckoutAddress(
                    "Medellín",
                    "CO",
                    "+5749876543",
                    "050505",
                    "Antioquia",
                    "Calle 456"
                ),
                "9876543210",
                "CC",
                "comprador2@placetopay.com",
                "+573009876543",
                "Ana",
                "Gómez"
            )
        ),
        BuyerPackage(
            id = 3,
            title = "Buyer from Cali",
            buyer = CheckoutBuyer(
                CheckoutAddress(
                    "Cali",
                    "CO",
                    "+5722222222",
                    "660066",
                    "Valle del Cauca",
                    "Avenida 789"
                ),
                "5555555555",
                "CC",
                "comprador3@placetopay.com",
                "+573005555555",
                "Pedro",
                "López"
            )
        ),
    )

    fun getBuyer(buyerId: Int): BuyerPackage? = getBuyerList().find { it.id == buyerId }
}