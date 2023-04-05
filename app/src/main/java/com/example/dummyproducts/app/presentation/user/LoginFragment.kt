package com.example.dummyproducts.app.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.common.createWaitAlertDialog
import com.example.dummyproducts.app.presentation.common.showShortToast
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModelFactory
import com.example.dummyproducts.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val userViewModel: UserViewModel by activityViewModels { UserViewModelFactory(context = requireContext().applicationContext) }
    private val waitDialog: AlertDialog by lazy {
        requireActivity().createWaitAlertDialog(
            title = getString(R.string.dialog_login_title),
            message = getString(R.string.dialog_login_message)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            userViewModel.getLastUser(
                onSuccessGet = {
                    val user = userViewModel.liveDataUser.value
                    user?.let { findNavController().navigate(R.id.action_loginFragment_to_userAccountFragment) }
                },
                onErrorGet = {
//                    setContentView(binding.root)
                }
            )

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
                                requireContext().showShortToast(text = getString(R.string.toast_successful_login))
                                // Сохраняем пользователя в БД
                                userViewModel.saveUser(
                                    user = userViewModel.liveDataUser.value,
                                    onSuccessSave = {},
                                    onErrorSave = {}
                                )
                            },
                            onErrorLogin = {
                                waitDialog.dismiss()
                                requireContext().showShortToast(text = getString(R.string.toast_error_login))
                            }
                        )
                    }else {
                        requireContext().showShortToast(text = getString(R.string.toast_empty_field))
                    }
                }else {
                    requireContext().showShortToast(text = getString(R.string.toast_empty_field))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}