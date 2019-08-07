package com.example.exchangerates.presentation.ui.register

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
import kotlinx.android.synthetic.main.fragment_reg_username.*

class RegUsernameFragment : Fragment(), ILoginView {

    private val presenter: LoginPresenter = LoginPresenter(this)

    override fun successLogin() {
    }

    override fun setErrorCode(isError: Boolean, type: Int) {
        if (type == 3) {
            if (isError) {
                layout_user_nick.error = getString(R.string.text_nickname_notification)
            } else {
                layout_user_nick.error = null
            }
            btn_enter_name.isEnabled = !isError
            layout_user_nick.isErrorEnabled = isError
        } else {
            Toast.makeText(context, "Код ошибки $type", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reg_username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_text_user_nick.setText(savedInstanceState?.getString("userNick"))

        layout_user_nick.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.checkNickname(s.toString())
            }
        })

        btn_enter_name.setOnClickListener {
            if (presenter.checkNickname(edit_text_user_nick.text.toString()))
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(
                        R.id.login_frame_layout,
                        RegUsermailFragment.newInstance(edit_text_user_nick.text.toString()),
                        "RegMail"
                    )
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        edit_text_user_nick.text.let {
            outState.putString("userNick", it.toString())
        }
        super.onSaveInstanceState(outState)
    }


}