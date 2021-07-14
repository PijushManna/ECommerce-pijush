package com.blogspot.ecommerce_pijush.model

data class User(
    val fullName: String,
    val number: String,
    val area:String,
    val address: String,
    val email: String,
    val birthDate: String,
    val password: String
) {
    constructor():this("","","","","","","")
}
