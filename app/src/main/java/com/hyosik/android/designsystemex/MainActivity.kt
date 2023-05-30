package com.hyosik.android.designsystemex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hyosik.android.designsystemex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.btnPrimarySmall.setOnClickListener {  }
        binding.btnSecondarySmall.setOnClickListener {  }

        binding.cbCheckBox.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked) Toast.makeText(this , "checked" , Toast.LENGTH_SHORT).show()
            else Toast.makeText(this , "unChecked" , Toast.LENGTH_SHORT).show()
        }
    }
}