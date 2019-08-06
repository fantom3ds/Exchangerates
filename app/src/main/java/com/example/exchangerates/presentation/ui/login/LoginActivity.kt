package com.example.exchangerates.presentation.ui.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.exchangerates.App
import com.example.exchangerates.R
import com.example.exchangerates.presentation.ui.rates.RatesActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(al_toolbar)

        if (App.instance.token != null) {
            startActivity(Intent(this, RatesActivity::class.java))
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.login_frame_layout, LoginFragment(), "LogFr")
            .addToBackStack(null)
            .commit()

        al_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("LogFr")?.isVisible == true) {
            AlertDialog.Builder(this).setTitle("Вы действительно хотите выйти из приложения?")
                .setPositiveButton("Да", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, arg1: Int) {
                        finish()
                    }
                })
                .setNegativeButton("Нет", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, arg1: Int) {
                        dialog.dismiss()
                    }
                }).create().show()
        }
        else{
            super.onBackPressed()
        }
    }
}
