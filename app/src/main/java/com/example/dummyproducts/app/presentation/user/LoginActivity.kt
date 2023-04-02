package com.example.dummyproducts.app.presentation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.common.createWaitAlertDialog
import com.example.dummyproducts.app.presentation.common.showShortToast
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModelFactory
import com.example.dummyproducts.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(context = applicationContext) }
    private val waitDialog: AlertDialog by lazy {
        this.createWaitAlertDialog(
            title = getString(R.string.dialog_login_title),
            message = getString(R.string.dialog_login_message)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        userViewModel.getLastUser(
            onSuccessGet = {

            },
            onErrorGet = {
                setContentView(binding.root)
            }
        )

        userViewModel.liveDataUser.observe(this) { user ->
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(android.R.id.content, UserAccountFragment())
                .commit()
        }

        with(binding) {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                if (username.isNotBlank()) {
                    val password = etPassword.text.toString()
                    if (password.isNotBlank()) {
                        waitDialog.show()

                        userViewModel.login(
                            userName = username.trim(),
                            password = password.trim(),
                            onSuccessLogin = {
                                waitDialog.dismiss()
                                this@LoginActivity.showShortToast(text = getString(R.string.toast_successful_login))
                                // Сохраняем пользователя в БД
                                userViewModel.saveUser(
                                    user = userViewModel.liveDataUser.value,
                                    onSuccessSave = {},
                                    onErrorSave = {}
                                )
                            },
                            onErrorLogin = {
                                waitDialog.dismiss()
                                this@LoginActivity.showShortToast(text = getString(R.string.toast_error_login))
                            }
                        )
                    }else {
                        this@LoginActivity.showShortToast(text = getString(R.string.toast_empty_field))
                    }
                }else {
                    this@LoginActivity.showShortToast(text = getString(R.string.toast_empty_field))
                }
            }
        }
    }
}