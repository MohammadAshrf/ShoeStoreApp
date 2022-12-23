package com.udacity.shoestore.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        binding.login.setOnClickListener {
            loginViewModel.login()
        }

        loginViewModel.loginSuccess.observe(viewLifecycleOwner) { loginSuccessfully ->
            if (loginSuccessfully) {
                findNavController().navigate(LoginFragmentDirections.actionLoginDestinationToWelcomeDestination())
            }
        }

        loginViewModel.loginFailed.observe(viewLifecycleOwner) { loginFailed ->
            if (loginFailed) {
                loginFailedMessage(R.string.login_failed)
            }
        }
        binding.newLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginDestinationToWelcomeDestination())
        }

    }

    private fun loginFailedMessage(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}