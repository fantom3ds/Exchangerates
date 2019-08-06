package com.example.exchangerates.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exchangerates.App
import com.example.exchangerates.R
import com.example.exchangerates.isEmail
import com.example.exchangerates.isPassword
import com.example.exchangerates.presentation.ui.rates.RatesActivity
import com.example.exchangerates.presentation.ui.register.RegUsernameFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                if (edit_text_password.text.toString().isPassword() &&
                    edit_text_email.text.toString().isEmail()
                ) {
                    App.instance.logIn(
                        "User",
                        edit_text_email.text.toString(),
                        edit_text_password.text.toString()
                    )
                    startActivity(Intent(context, RatesActivity::class.java))
                    activity?.finish()
                } else {
                    //Чтобы сработали регулярки и выдали ошибки
                    if (edit_text_email.text.toString().isEmpty()) {
                        edit_text_email.setText("")
                    }
                    if (edit_text_password.text.toString().isEmpty()) {
                        edit_text_password.setText("")
                    }
                    btn_login.isEnabled = false
                }
            }

            R.id.btn_forgot_pass -> {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(
                        R.id.login_frame_layout,
                        ForgotPassFragment(), "ForgotFr"
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            }

            R.id.btn_register -> {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(
                        R.id.login_frame_layout,
                        RegUsernameFragment(), "RegFr"
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        btn_forgot_pass.setOnClickListener(this)

        layout_email.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    layout_email.isErrorEnabled = true
                    layout_email.error = getString(R.string.text_email_empty_notification)
                    btn_login.isEnabled = false
                } else {
                    if (!s.toString().isEmail()) {
                        layout_email.isErrorEnabled = true
                        layout_email.error = getString(R.string.text_email_notification)
                        btn_login.isEnabled = false
                    } else {
                        layout_email.isErrorEnabled = false
                        btn_login.isEnabled = true
                    }
                }
            }
        })

        layout_password.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 6) {
                    layout_password.isErrorEnabled = true
                    layout_password.error = getString(R.string.text_password_size_notification)
                    btn_login.isEnabled = false
                } else
                    if (!s.toString().isPassword()) {
                        layout_password.isErrorEnabled = true
                        layout_password.error = getString(R.string.text_password_notification)
                        btn_login.isEnabled = false
                    } else {
                        layout_password.isErrorEnabled = false
                        btn_login.isEnabled = true
                    }
            }
        })
    }
}
