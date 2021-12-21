package com.example.foodtracking.viewModel

import com.example.foodtracking.model.Gender
import com.example.foodtracking.model.User

class RegisterViewModel {
    private var gender = Gender.Male

    fun setGender(gender: Gender) {
        this.gender = gender
    }

    fun check(inputs: User): String {
        var message = ""

        // Check Age
        inputs.age.let {
            if (it <= 8 || it >= 100) {
                message += "Enter a valid age\n"
            }
        }

        // Check Height
        inputs.height.let {
            if (it <= 100 || it >= 300) {
                message += "Enter a valid height\n"
            }
        }

        // Check Weight
        inputs.weight.let {
            if (it <= 20 || it >= 300) {
                message += "Enter a valid weight\n"
            }
        }

        return message
    }
}