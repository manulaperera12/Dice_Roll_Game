package com.example.dicerollapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow

class Start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val aboutButton = findViewById<Button>(R.id.aboutButton)
        val newGameButton = findViewById<Button>(R.id.newGameButton)

        aboutButton.setOnClickListener {
//          create a popup window
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.about_popup, null)
            val popupWindow = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//          set the location to the center of screen
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
//          when click outside of popup window it will close
            popupWindow.isOutsideTouchable = true
        }

        newGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
//          start next activity (new game activity)
            startActivity(intent)
        }

    }
}