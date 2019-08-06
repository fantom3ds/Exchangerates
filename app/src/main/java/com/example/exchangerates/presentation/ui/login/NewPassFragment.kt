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
import com.example.exchangerates.isPassword
import com.example.exchangerates.presentation.ui.rates.RatesActivity
import kotlinx.android.synthetic.main.fragment_new_pass.*

class NewPassFragment :Fragment(){

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
        return inflater.inflate(R.layout.fragment_new_pass,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        layout_new_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 6) {
                    layout_new_pass.isErrorEnabled = true
                    layout_new_pass.error = getString(R.string.text_password_size_notification)
                    btn_login_new.isEnabled = false
                } else {
                    if (!s.toString().isPassword()) {
                        layout_new_pass.isErrorEnabled = true
                        layout_new_pass.error = getString(R.string.text_password_notification)
                        btn_login_new.isEnabled = false
                    } else {
                        layout_new_pass.isErrorEnabled = false
                        btn_login_new.isEnabled = true
                    }
                }
            }
        })




        layout_reply_user_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 6) {
                    layout_reply_user_pass.isErrorEnabled = true
                    layout_reply_user_pass.error = getString(R.string.text_password_size_notification)
                    btn_login_new.isEnabled = false
                } else
                    if (edit_text_new_pass.text.toString() != edit_text_reply_new_pass.text.toString()) {
                        layout_reply_user_pass.isErrorEnabled = true
                        layout_reply_user_pass.error = getString(R.string.text_reply_pass_notification)
                        btn_login_new.isEnabled = false
                    } else {
                        layout_reply_user_pass.isErrorEnabled = false
                        layout_reply_user_pass.error = null
                        btn_login_new.isEnabled = true
                    }
            }
        })




        btn_login_new.setOnClickListener {
            if (layout_new_pass.isErrorEnabled || layout_reply_user_pass.isErrorEnabled) {
                it.isEnabled = false
            } else {
                App.instance.logIn(
                    App.instance.userName,
                    arguments?.getString("Mail"),
                    edit_text_new_pass.text.toString()
                )
                startActivity(Intent(context, RatesActivity::class.java))
                activity?.finish()
            }
        }

    }

}