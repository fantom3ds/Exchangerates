package com.example.exchangerates.presentation.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exchangerates.App
import com.example.exchangerates.R
import com.example.exchangerates.isEmail
import kotlinx.android.synthetic.main.fragment_forgot_pass.*

class ForgotPassFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_user_mail_remind_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    layout_user_mail_remind_pass.isErrorEnabled = true
                    layout_user_mail_remind_pass.error = getString(R.string.text_email_empty_notification)
                    btn_remind_pass.isEnabled = false
                } else {
                    if (!s.toString().isEmail()) {
                        layout_user_mail_remind_pass.isErrorEnabled = true
                        layout_user_mail_remind_pass.error = getString(R.string.text_email_notification)
                        btn_remind_pass.isEnabled = false
                    } else {
                        layout_user_mail_remind_pass.isErrorEnabled = false
                        btn_remind_pass.isEnabled = true
                    }
                }
            }
        })


        btn_remind_pass.setOnClickListener {
            edit_text_user_mail_remind_pass.text.toString().apply {
                if (isEmail()) {
                    if (this == App.instance.userMail) {
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                R.id.login_frame_layout,
                                NewPassFragment.newInstance(edit_text_user_mail_remind_pass.text.toString())
                            )
                            ?.addToBackStack(null)
                            ?.commit()

                    } else {
                        Toast.makeText(context, "Такой пользователь не залогинен", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}