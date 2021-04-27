package com.example.mtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var payments: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        payments = findViewById(R.id.paymentLayout)

        payments.setOnClickListener {



            var intent = Intent(this, PaymentActivity::class.java)

            startActivity(intent)

        }

    }
}