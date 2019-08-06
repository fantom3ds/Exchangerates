package com.example.exchangerates.presentation.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exchangerates.R
import com.example.exchangerates.isEmail
import kotlinx.android.synthetic.main.fragment_reg_usermail.*

class RegUsermailFragment : Fragment() {

    companion object {
        fun newInstance(nickname: String): RegUsermailFragment {
            val args = Bundle()
            args.putString("userNick", nickname)//кладем туда ник
            val flag = RegUsermailFragment()
            flag.arguments = args
            return flag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_usermail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        edit_text_user_mail.setText(savedInstanceState?.getString("userMail"))

        arguments?.getString("userNick").let {
            tv_header_text.setText("$it, введите адрес электронной почты")
        }

        btn_enter_mail.setOnClickListener {
            if (edit_text_user_mail.text.toString().isEmail()) {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(
                        R.id.login_frame_layout,
                        RegPassFragment.newInstance(
                            arguments?.getString("userNick"),
                            edit_text_user_mail.text.toString()
                        )
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            } else {
                btn_enter_mail.isEnabled = false
            }
        }

        layout_user_mail.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    layout_user_mail.isErrorEnabled = true
                    layout_user_mail.error = getString(R.string.text_email_empty_notification)
                    btn_enter_mail.isEnabled = false
                } else {
                    if (!s.toString().isEmail()) {
                        layout_user_mail.isErrorEnabled = true
                        layout_user_mail.error = getString(R.string.text_email_notification)
                        btn_enter_mail.isEnabled = false
                    } else {
                        layout_user_mail.isErrorEnabled = false
                        btn_enter_mail.isEnabled = true
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {

        edit_text_user_mail.text.let {
            outState.putString("userMail", it.toString())
        }
        super.onSaveInstanceState(outState)
    }
}