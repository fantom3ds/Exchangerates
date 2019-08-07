package com.example.exchangerates.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exchangerates.R
import com.example.exchangerates.presentation.presenter.ILoginView
import com.example.exchangerates.presentation.presenter.LoginPresenter
import com.example.exchangerates.presentation.ui.rates.RatesActivity
import com.example.exchangerates.presentation.ui.register.RegUsernameFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), View.OnClickListener, ILoginView {

    val presenter = LoginPresenter(this)



    override fun setErrorCode(isError: Boolean, type: Int) {
        when (type) {
            //1 - неверный e-mail
            1 -> {
                if (isError) {
                    layout_email.error = getString(R.string.text_email_notification)
                    layout_email.isErrorEnabled = true
                    btn_login.isEnabled = false
                } else {
                    layout_email.error = null
                    layout_email.isErrorEnabled = false
                    btn_login.isEnabled = true
                }
            }
            //2 - пароль неверен
            2 -> {
                if (isError) {
                    layout_password.error = getString(R.string.text_password_notification)
                    layout_password.isErrorEnabled = true
                    btn_login.isEnabled = false
                } else {
                    layout_password.error = null
                    layout_password.isErrorEnabled = false
                    btn_login.isEnabled = true
                }
            }
            //21 - пароль короткий
            21 -> {
                if (isError) {
                    layout_password.error = getString(R.string.text_password_size_notification)
                    layout_password.isErrorEnabled = true
                    btn_login.isEnabled = false
                } else {
                    layout_password.error = null
                    layout_password.isErrorEnabled = false
                    btn_login.isEnabled = true
                }
            }
            //других ошибок тут быть не может, выводим что есть
            else -> {
                if (isError) {
                    Toast.makeText(context, "Код ошибки $type", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun successLogin() {
        startActivity(Intent(context, RatesActivity::class.java))
        activity?.finish()
    }



    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                presenter.login(edit_text_email.text.toString(), edit_text_password.text.toString(), true)
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
                presenter.checkEmail(s.toString())
            }
        })

        layout_password.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })
    }
}
