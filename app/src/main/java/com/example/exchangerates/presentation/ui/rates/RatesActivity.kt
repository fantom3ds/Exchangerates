package com.example.exchangerates.presentation.ui.rates

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.exchangerates.App
import com.example.exchangerates.R
import com.example.exchangerates.presentation.presenter.IRatesView
import com.example.exchangerates.presentation.presenter.RatesPresenter
import com.example.exchangerates.presentation.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_rates.*


class RatesActivity : AppCompatActivity(), IRatesView {

    private var alertDialog: AlertDialog? = null
    private val presenter = RatesPresenter(this)
    lateinit var username: String

    override fun showRates(rates: String) {
        tv_rates.text = rates
    }

    override fun setLoading(isLoading: Boolean) {
        alertDialog?.dismiss()
        if (isLoading) {
            alertDialog = AlertDialog.Builder(this)
                .setView(R.layout.alert_dialog_loading)
                .setTitle("Подождите, данные загружаются")
                .setCancelable(false)
                .create()
            alertDialog?.show()
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        presenter.showRates("RUB")
        setSupportActionBar(pcf_toolbar)

        if (App.instance.userName == null) {
            username = App.instance.userMail.toString()
            tv_user_mail.text = username
            tv_user_name.text = username
        } else {
            username = App.instance.userName.toString()
            tv_user_mail.text = App.instance.userMail
        }
        tv_user_name.text = username


        // Настраиваем адаптер для спиннера
        val adapter =
            ArrayAdapter.createFromResource(this, R.array.Rates, R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.Rates)
                presenter.showRates(choose[selectedItemPosition])
                Toast.makeText(
                    applicationContext,
                    "Показываю курс для валюты ${choose[selectedItemPosition]}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.m_logout -> {
                AlertDialog.Builder(this)
                    .setTitle("${App.instance.userName},вы действительно хотите выйти из своего аккаунта?")
                    .setPositiveButton("Да", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, arg1: Int) {
                            presenter.logout()
                        }
                    })
                    .setNegativeButton("Нет", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, arg1: Int) {
                            dialog.dismiss()
                        }
                    }).create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


