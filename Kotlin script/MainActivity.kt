package com.halac123b.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var textInput: TextView? = null

    // Biến check xem inputText có đang kết thúc bằng số hay dấu chấm
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.textInput)
    }

    fun onDigit(view: View){
        textInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        textInput?.text = ""
    }

    fun onDot(view: View){
        if (lastNumeric && !textInput?.text?.contains(".")!!){
            textInput?.append(".")
        }
    }

    fun onOperator(view: View){
        textInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                textInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    // Check xem operator đã đc thêm vào input tex hay chưa.
    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }
        else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var textInputString = textInput?.text.toString()
            var prefix = ""
            try {
                if (textInputString.startsWith("-")){
                    prefix = "-"
                    textInputString = textInputString.substring(1)
                }

                if (textInputString.contains("-")){
                    val parameter = textInputString.split("-")
                    var one = parameter[0]
                    val two = parameter[1]
                    if (prefix == "-"){
                        one = prefix + one
                    }
                    textInput?.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }
                else if (textInputString.contains("+")){
                    val parameter = textInputString.split("+")
                    var one = parameter[0]
                    val two = parameter[1]
                    if (prefix == "-"){
                        one = prefix + one
                    }
                    textInput?.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }
                else if (textInputString.contains("*")){
                    val parameter = textInputString.split("*")
                    var one = parameter[0]
                    val two = parameter[1]
                    if (prefix == "-"){
                        one = prefix + one
                    }
                    textInput?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }
                else if (textInputString.contains("/")){
                    val parameter = textInputString.split("/")
                    var one = parameter[0]
                    val two = parameter[1]
                    if (prefix == "-"){
                        one = prefix + one
                    }
                    textInput?.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(result: String): String{
        var value = result
        if (value.substring(value.length - 2) == ".0"){
            value = value.substring(0, value.length - 2)
        }
        return value
    }
}