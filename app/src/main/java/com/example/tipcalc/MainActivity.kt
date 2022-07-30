package com.example.tipcalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateTip.setOnClickListener{calcTip()}
        binding.billAmountEditText.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }
    private fun calcTip(){
        val cost = binding.billAmountEditText.text.toString().toDoubleOrNull()
        if(cost == null){
            binding.finalTip.text=""
            val toast = Toast.makeText(this,"Amount cannot be empty",Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        val tipPercentage = when(binding.serviceOption.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.15
            else -> 0.10
        }
        var tip= cost*tipPercentage
        if(binding.roundUp.isChecked){
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.finalTip.text = getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}