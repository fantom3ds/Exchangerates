package com.example.exchangerates.presentation.ui.register

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
import kotlinx.android.synthetic.main.fragment_reg_usermail.*

class RegUsermailFragment : Fragment(), ILoginView {

    private val presenter = LoginPresenter(this)

    override fun successLogin() {
    }

    override fun setErrorCode(isError: Boolean, type: Int) {
        if (isError)
            layout_user_mail.error = getString(R.string.text_email_notification)
        else
            layout_user_mail.error = null

        layout_user_mail.isErrorEnabled = isError
        btn_enter_mail.isEnabled = !isError
    }

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
            if (presenter.checkEmail(edit_text_user_mail.text.toString())) {
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
            }
        }

        layout_user_mail.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.checkNickname(s.toString())
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