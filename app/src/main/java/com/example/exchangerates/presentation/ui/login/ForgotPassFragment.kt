package com.example.exchangerates.presentation.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.exchangerates.R
import com.example.exchangerates.presentation.presenter.ILoginView
import com.example.exchangerates.presentation.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_forgot_pass.*

class ForgotPassFragment : Fragment(), ILoginView {

    override fun successLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setErrorCode(isError: Boolean, type: Int) {
        when (type) {
            0 -> {
                if (isError)
                    Toast.makeText(context, getString(R.string.text_user_not_exists), Toast.LENGTH_LONG).show()
            }
            1 -> {
                if (isError)
                    layout_user_mail_remind_pass.error = getString(R.string.text_email_notification)
                else
                    layout_user_mail_remind_pass.error = null
                layout_user_mail_remind_pass.isErrorEnabled = isError
                btn_remind_pass.isEnabled = !isError
            }
            else -> {

            }
        }
    }

    private val presenter = LoginPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Восстановление пароля"

        layout_user_mail_remind_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })

        btn_remind_pass.setOnClickListener {
            edit_text_user_mail_remind_pass.text.toString().apply {
                if (presenter.checkEmail(this))
                    if (presenter.userExists(this)) {
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(
                                R.id.login_frame_layout,
                                NewPassFragment.newInstance(this)
                            )
                            ?.addToBackStack(null)
                            ?.commit()
                    }
            }
        }
    }

}