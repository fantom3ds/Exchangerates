package com.example.exchangerates.presentation.ui.register

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
import kotlinx.android.synthetic.main.fragment_user_pass.*

class RegPassFragment : Fragment(), ILoginView {

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
                    layout_user_pass.error = getString(R.string.text_password_notification)
                else
                    layout_user_pass.error = null
                layout_user_pass.isErrorEnabled = isError
                btn_register_now.isEnabled = !isError
            }
            //21 - пароль короткий
            21 -> {
                if (isError)
                    layout_user_pass.error = getString(R.string.text_password_size_notification)
                else
                    layout_user_pass.error = null
                layout_user_pass.isErrorEnabled = isError
                btn_register_now.isEnabled = !isError
            }
            //22 - пароли не совпадают
            22 -> {
                if (isError)
                    layout_reply_user_pass.error = getString(R.string.text_reply_pass_notification)
                else
                    layout_reply_user_pass.error = null
                layout_reply_user_pass.isErrorEnabled = isError
                btn_register_now.isEnabled = !isError
            }
        }
    }

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
                presenter.checkPassword(s.toString())
            }
        })

        layout_reply_user_pass.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                presenter.comparePasswords(s.toString(),edit_text_user_pass.text.toString())
            }
        })

        btn_register_now.setOnClickListener {
            presenter.register(
                arguments?.getString("Mail").toString(),
                edit_text_user_pass.text.toString(),
                arguments?.getString("Nick").toString()
            )
        }
    }
}