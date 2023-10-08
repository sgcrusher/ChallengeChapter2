package com.sg.challengechap2.data.dummy

import com.sg.challengechap2.R
import com.sg.challengechap2.model.Food

interface FoodDataSource {
    fun getFoodData(): List<Food>
}

class FoodDataSourceImpl() : FoodDataSource {
    override fun getFoodData(): List<Food> {
        return mutableListOf(
            Food(
                foodId = 1,
                foodName = "Ayam Panggang",
                foodDescription = "Ayam Panggang : Daging Ayam yang dimasak sebagai hidangan dengan cara dipanggang.",
                foodPrice = 30000.0,
                foodImg = R.drawable.img_menu_1,
                foodShopDistance = 1.0,
                foodShopLocation = "Jl. Antara No.23, Klp. Tiga, Kec. Tj. Karang Pusat, Kota Bandar Lampung, Lampung 35118",
                foodUrllocation = "https://maps.app.goo.gl/JNvoNrTZDhRpSkTQ8"
            ),
            Food(
                foodId = 2,
                foodName = "Nasi Kebuli",
                foodDescription = "Nasi Kebuli: Hidangan nasi kaya akan rempah dengan aroma khas bercita rasa gurih.",
                foodPrice = 25000.0,
                foodImg = R.drawable.img_menu_2,
                foodShopDistance = 3.0,
                foodShopLocation = "Jl. Gajah Mada No.54, Kota Baru, Kec. Tanjungkarang Timur, Kota Bandar Lampung,Lampung 35121",
                foodUrllocation = "https://maps.app.goo.gl/XVXw2fcoxb8rXGUh9"
            ),
            Food(
                foodId = 3,
                foodName = "Es Teler",
                foodDescription = "Es Teler: koaktail buah asli Indonesia.",
                foodPrice = 20000.0,
                foodImg = R.drawable.img_menu_3,
                foodShopDistance = 2.0,
                foodShopLocation = "Jl. P. Antasari No.88, Tj. Baru, Kec. Kedamaian, Kota Bandar Lampung,Lampung 35122",
                foodUrllocation = "https://maps.app.goo.gl/V3VsgWF6hED8H1qx9"
            ),
            Food(
                foodId = 4,
                foodName = "Cumi Tepung",
                foodDescription = "Cumi Tepung: Cumi yang digoreng dengan tepung.",
                foodPrice = 35000.0,
                foodImg = R.drawable.img_menu_4,
                foodShopDistance = 1.5,
                foodShopLocation = "Jl. P. Antasari, Tj. Baru, Kec. Kedamaian, Kota Bandar Lampung, Lampung 35133",
                foodUrllocation = "https://maps.app.goo.gl/PACHHLxirZxcSBgV9"
            ),


            )
    }
}