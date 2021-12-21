package com.example.foodtracking.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import com.example.foodtracking.R
import com.example.foodtracking.model.Food
import com.example.foodtracking.model.Gender
import com.example.foodtracking.model.User
import com.example.foodtracking.viewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel = RegisterViewModel()

    private lateinit var txtWarning: TextView
    private lateinit var txtEditHeight: EditText
    private lateinit var txtEditWeight: EditText
    private lateinit var txtEditAge: EditText
    private lateinit var toggle: ToggleButton
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindViews()
    }

    private fun bindViews() {
        txtWarning = findViewById(R.id.txtWarning)
        txtEditHeight = findViewById(R.id.txtEditHeight)
        txtEditWeight = findViewById(R.id.txtEditWeight)
        txtEditAge = findViewById(R.id.txtEditAge)
        btnContinue = findViewById(R.id.btnContinue)
        toggle = findViewById(R.id.btnGengerToggle)
        setAttributes()
        setActions()
    }

    private fun setAttributes() {
        val user = intent.getSerializableExtra("user") as User
        if (user.height != -1) {
            txtEditAge.setText(user.age.toString())
            txtEditHeight.setText(user.height.toString())
            txtEditWeight.setText(user.weight.toInt().toString())
            toggle.isChecked = user.gender == Gender.Female
            btnContinue.setText("Done")
        }
    }

    private fun setActions() {
        setToggleButton()
        setContinueButton()
    }

    private fun setToggleButton() {
        toggle.setOnCheckedChangeListener { _, isChecked ->
            val gender = if (isChecked) Gender.Female else Gender.Male
            viewModel.setGender(gender)
        }
    }

    private fun setContinueButton() {
        btnContinue.setOnClickListener {
            val user = createUser()

            val message = viewModel.check(user)
            if (message == "") {
                save(user)
                val activity = Intent(this, FoodActivity::class.java)
                val foods = intent.getSerializableExtra("foods") as ArrayList<Food>
                activity.putExtra("user",user)
                activity.putExtra("foods",foods)
                startActivity(activity)
                finish()
            } else {
                txtWarning.text = message
            }
        }
    }

    private fun createUser(): User {
        val height = if (txtEditHeight.text.isEmpty()) "0" else txtEditHeight.text
        val weight = if (txtEditWeight.text.isEmpty()) "0" else txtEditWeight.text
        val age = if (txtEditAge.text.isEmpty()) "0" else txtEditAge.text

        val user = User(
            height = height.toString().toInt(),
            weight = weight.toString().toDouble(),
            age = age.toString().toInt(),
            gender = if (toggle.isChecked) Gender.Female else Gender.Male
        )
        return user
    }

    private fun save(user: User) {
        val height = user.height
        val weight = user.weight.toFloat()
        val age = user.age
        val gender = if (toggle.isChecked) "Female" else "Male"

        val sharedPreferences = getSharedPreferences("com.example.foodtracking", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("height", height).apply()
        sharedPreferences.edit().putInt("age", age).apply()
        sharedPreferences.edit().putFloat("weight", weight).apply()
        sharedPreferences.edit().putString("gender", gender).apply()
    }
}