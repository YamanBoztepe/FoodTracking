package com.example.foodtracking.model

import java.io.Serializable

enum class Gender {
    Male, Female
}

class User(val height: Int, val weight: Double, val age: Int, val gender: Gender): Serializable