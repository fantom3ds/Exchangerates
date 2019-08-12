package com.example.exchangerates.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exchangerates.R
import com.example.exchangerates.presentation.presenter.ILoginView
import com.example.exchangerates.presentation.presenter.LoginPresenter
import com.example.exchangerates.presentation.ui.rates.RatesActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_new_pass.*

class NewPassFragment : Fragment(), ILoginView {

    private val presenter = LoginPresenter(this)

    override fun successLogin() {
        startActivity(Intent(context, RatesActivity::class.java))
        activity?.finish()
    }

    override fun setErrorCode(isError: Boolean, type: Int) {
        when (type) {
            //2 - пароль неверен
            2 -> {
                if (isError)
                    layout_new_pass.error = getString(R.string.text_password_notification)
                else
                    layout_new_pass.error = null
                layout_new_pass.isErrorEnabled = isError
                btn_login_new.isEnabled = !isError
            }
            //21 - пароль короткий
            21 -> {
                if (isError)
                    layout_new_pass.error = getString(R.string.text_password_size_notification)
                else
                    layout_new_pass.error = null
                layout_new_pass.isErrorEnabled = isError
                btn_login_new.isEnabled = !isError
            }
            //22 - пароли не совпадают
            22 -> {
                if (isError)
                    layout_reply_user_pass.error = getString(R.string.text_reply_pass_notification)
                else
                    layout_reply_user_pass.error = null
                layout_reply_user_pass.isErrorEnabled = isError
                btn_login_new.isEnabled = !isError
            }
        }
    }

    companion object {
        fun newInstance(userMail: String): NewPassFragment {
            val args = Bundle()//пустой объект чтобы туда что-то класть
            args.putString("Mail", userMail)
            val flag = NewPassFragment()//создаем Instance нового фрагмента
            flag.arguments = args //
            return flag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        al_toolbar?.title = "Восстановление пароля"

        layout_new_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })

        layout_reply_user_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.comparePasswords(s.toString(), edit_text_new_pass.text.toString())
            }
        })

        btn_login_new.setOnClickListener {
            if (layout_new_pass.isErrorEnabled || layout_reply_user_pass.isErrorEnabled) {
                it.isEnabled = false
            } else {
                presenter.login(arguments?.getString("Mail").toString(), edit_text_new_pass.text.toString(), false)
            }
        }

    }

}