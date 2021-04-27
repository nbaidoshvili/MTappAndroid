package com.example.mtapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class PaymentActivity : AppCompatActivity() {

    private lateinit var typeInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var numberInput: EditText
    private lateinit var monthInput: EditText
    private lateinit var yearInput: EditText
    private lateinit var cvvInput: EditText
    private lateinit var button: Button

    private lateinit var typeError: TextView
    private lateinit var nameError: TextView
    private lateinit var numberError: TextView
    private lateinit var dateError: TextView
    private lateinit var cvvError: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        typeInput = findViewById(R.id.typeInput)
        nameInput = findViewById(R.id.nameInput)
        numberInput = findViewById(R.id.numberInput)
        monthInput = findViewById(R.id.monthInput)
        yearInput = findViewById(R.id.yearInput)
        cvvInput = findViewById(R.id.cvvInput)
        button = findViewById(R.id.buyButton)

        typeError = findViewById(R.id.typeError)
        nameError = findViewById(R.id.nameError)
        numberError = findViewById(R.id.numberError)
        dateError = findViewById(R.id.dateError)
        cvvError = findViewById(R.id.cvvError)


        button.setOnClickListener {


            writeError(nameError, nameInput, "^([a-z]+\\s)+[a-z]+\$".toRegex())

            if(writeError(typeError, typeInput, "^(amex|mastercard|visa)\$".toRegex()) == "valid"){
                if(typeInput.text.toString().toLowerCase() == "amex"){
                    writeError(numberError, numberInput, "^[0-9]{15}\$".toRegex())
                    writeError(cvvError, cvvInput, "^[0-9]{4}\$".toRegex())
                }
                else{
                    writeError(numberError, numberInput, "^[0-9]{16}\$".toRegex())
                    writeError(cvvError, cvvInput, "^[0-9]{3}\$".toRegex())
                }
            }
            else{
                writeError(numberError, numberInput, defaultError = "Your card type is incorrect")
                writeError(cvvError, cvvInput, defaultError = "Your card type is incorrect")
            }



            if(writeError(dateError, monthInput, "^0[1-9]\$|^1[0-2]\$".toRegex())=="valid" &&
                writeError(dateError, yearInput, "^[0-9]{2}\$".toRegex()) == "valid") {

                val currentMonth = SimpleDateFormat("MM").format(Date()).toInt()
                val currentYear = SimpleDateFormat("yy").format(Date()).toInt()

                val month = monthInput.text.toString().toInt()
                val year = yearInput.text.toString().toInt()


                when {
                    year > currentYear -> {
                        writeError(dateError, monthInput, defaultError = "none")
                    }
                    year == currentYear && month > currentMonth -> {
                        writeError(dateError, monthInput, defaultError = "none")
                    }
                    else -> {
                        writeError(dateError, monthInput, defaultError = "Card is expired")
                    }
                }
            }


            if(typeError.text == "" &&
                nameError.text == "" &&
                numberError.text == "" &&
                dateError.text == "" &&
                cvvError.text == ""){

                typeInput.setText("")
                nameInput.setText("")
                numberInput.setText("")
                monthInput.setText("")
                yearInput.setText("")
                cvvInput.setText("")

                Toast.makeText(this,"Transaction Successful",Toast.LENGTH_SHORT).show();
            }

        }
    }



    private fun writeError(field: TextView, input: EditText, regex: Regex = "".toRegex(), defaultError: String = ""): String {


        when {
            defaultError.isNotEmpty() -> {

                if(defaultError == "none"){
                    field.text = ""
                    field.setBackgroundColor(Color.parseColor("#fafafa"))
                    return "valid"
                }

                field.text = defaultError
                field.setBackgroundColor(Color.parseColor("#ffd1d1"))
                return "default"
            }
            input.text.toString().toLowerCase().isEmpty() -> {
                field.text = "Field is empty"
                field.setBackgroundColor(Color.parseColor("#ffd1d1"))
                return "empty"

            }
            !input.text.toString().toLowerCase().matches(regex) -> {
                field.text = "Invalid input"
                field.setBackgroundColor(Color.parseColor("#ffd1d1"))
                return "invalid"

            }
            else -> {
                field.text = ""
                field.setBackgroundColor(Color.parseColor("#fafafa"))
                return "valid"
            }
        }
    }
}

