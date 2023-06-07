package com.example.rentcars

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rentcars.databinding.FragmentSignInBinding
import com.example.rentcars.presentation.viewmodel.CarsViewModel
import com.example.rentcars.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private val binding: FragmentSignInBinding by viewBinding()
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore
    private val viewModel:ProfileViewModel by activityViewModels()
    private val carViewModel:CarsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        binding.btnLogin.setOnClickListener() {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            activity?.let { it1 ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it1) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                activity,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }.addOnSuccessListener {
                        val firebaseUser = it.user
                        firebaseUser?.getIdToken(true)?.addOnSuccessListener { result ->
                            val token = result.token
                           viewModel.saveToken(token.toString())
                            findNavController().navigate(
                                R.id.action_signInFragment_to_mainFlowFragment2
                            )
                        }
                    }
            }
        }
    }

}