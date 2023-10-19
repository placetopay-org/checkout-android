package com.placetopay.p2pr.data.cart

import com.placetopay.p2pr.data.checkout.base.CheckoutItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PackageRepository @Inject constructor() {

    fun getWelcomePackages() = listOf(
        WelcomePackage(
            id = 1,
            title = "Sample purchase with multiple products",
            description = "Listing of products with values from 25k to 100k for a single unit and zero tax.",
            items = mutableListOf(
                CheckoutItem("Mystery", "Objeto desconocido", 100_000, 1, 201, .0),
                CheckoutItem("Books", "Pergamino antiguo", 80_000, 1, 202, .0),
                CheckoutItem("Mystery", "Gema brillante", 50_000, 1, 203, .0),
                CheckoutItem("Home", "Llave dorada", 25_000, 1, 204, .0),
                CheckoutItem("Mystery", "Reliquia antigua", 30_000, 1, 205, .0),
                CheckoutItem("Home", "Estatuilla enigmática", 75_000, 1, 206, .0)
            )
        ),
        WelcomePackage(
            id = 2,
            title = "Sample purchase with a single product",
            description = "A single product with a value of 600k for a single unit and zero tax",
            items = mutableListOf(
                CheckoutItem("Electronic", "Smart Watch", 50_000, 1, 101, 9_500.0),
                //CheckoutItem("Electronic", "Smart Watch", 600_000, 1, 101, 114_000.0),
            )
        ),
        WelcomePackage(
            id = 3,
            title = "Sample purchase with multiple products",
            description = "Listing of products with values from 35k to 80k for a single unit and 19% tax.",
            items = mutableListOf(
                CheckoutItem("Books", "Libro antiguo", 80_000, 1, 302, 15_200.0),
                CheckoutItem("Clothes", "Camisa desconocida", 35_000, 2, 102, 6_650.0),
            )
        )
    )

    fun getWelcomePackage(packageId: Int) =
        getWelcomePackages().find { item -> item.id == packageId }

}