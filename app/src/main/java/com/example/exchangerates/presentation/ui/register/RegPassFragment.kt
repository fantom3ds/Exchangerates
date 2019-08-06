package com.example.exchangerates.presentation.ui.register

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
import kotlinx.android.synthetic.main.fragment_user_pass.*

class RegPassFragment : Fragment() {

    companion object {
        fun newInstance(userNick: String?, userMail: String?): RegPassFragment {
            val args = Bundle()//пустой объект чтобы туда что-то класть
            args.putString("Nick", userNick)
            args.putString("Mail", userMail)
            val flag = RegPassFragment()//создаем Instance нового фрагмента
            flag.arguments = args //
            return flag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        layout_user_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 6) {
                    layout_user_pass.isErrorEnabled = true
                    layout_user_pass.error = getString(R.string.text_password_size_notification)
                    btn_register_now.isEnabled = false
                } else {
                    if (!s.toString().isPassword()) {
                        layout_user_pass.isErrorEnabled = true
                        layout_user_pass.error = getString(R.string.text_password_notification)
                        btn_register_now.isEnabled = false
                    } else {
                        layout_user_pass.isErrorEnabled = false
                        btn_register_now.isEnabled = true
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
                    btn_register_now.isEnabled = false
                } else
                    if (edit_text_user_pass.text.toString() != edit_text_reply_user_pass.text.toString()) {
                        layout_reply_user_pass.isErrorEnabled = true
                        layout_reply_user_pass.error = getString(R.string.text_reply_pass_notification)
                        btn_register_now.isEnabled = false
                    } else {
                        layout_reply_user_pass.isErrorEnabled = false
                        layout_reply_user_pass.error = null
                        btn_register_now.isEnabled = true
                    }
            }
        })

        btn_register_now.setOnClickListener {
            if (layout_user_pass.isErrorEnabled || layout_reply_user_pass.isErrorEnabled) {
                it.isEnabled = false
            } else {
                App.instance.logIn(
                    arguments?.getString("Nick"),
                    arguments?.getString("Mail"),
                    edit_text_user_pass.text.toString()
                )
                startActivity(Intent(context, RatesActivity::class.java))
                activity?.finish()
            }
        }
    }
}