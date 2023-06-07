package com.example.rentcars.presentation.fragment

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.R
import com.example.rentcars.data.entity.ProfileEntity
import com.example.rentcars.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.InstallIn
import javax.inject.Singleton


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val binding: FragmentRegisterBinding by viewBinding()
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding.tvLogin.setOnClickListener() {

            findNavController().navigate(
                R.id.action_registerFragment_to_signInFragment
            )

        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val region = binding.etRegion.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(activity, "Введите email", Toast.LENGTH_LONG).show()

            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(activity, "Введите пароль", Toast.LENGTH_LONG).show()
            }
            activity?.let { it1 ->
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it1) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val user = mAuth.currentUser

                            val userForDb = user?.uid?.let { it2 ->
                                ProfileEntity(
                                    it2,
                                    name,
                                    phone,
                                    region
                                )
                                findNavController().navigate(R.id.action_registerFragment_to_signInFragment)
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)


                        }
                    }
            }
        }
    }


}