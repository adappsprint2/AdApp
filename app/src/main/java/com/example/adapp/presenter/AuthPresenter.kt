package com.example.adapp.presenter

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.adapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthPresenter(val view: View){

    fun createAccount(username: String, email: String, password: String, phoneNo: String) {
        val fAuth= FirebaseAuth.getInstance()
        fAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    val user=fAuth.currentUser
                    val userObj= User(username,email,password,phoneNo)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(user.uid).setValue(userObj)
                    view.sendToast("Registration is successful")

                }
                else
                {
                    view.sendToast("Unable to complete Registration..")
                }

            }
    }

    fun loginUser(userMail: String, userPassword: String) {
        val lAuth = FirebaseAuth.getInstance()
        lAuth.signInWithEmailAndPassword(userMail,userPassword)
            .addOnCompleteListener() { task->
                if(task.isSuccessful)
                {
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user.isEmailVerified)
                    {
                        view.sendToast("Logged In successfully")
                    }
                    else
                    {
                        user.sendEmailVerification()
                        view.sendToast("Verification mail sent..")
                    }
                }
                else
                {
                    view.sendToast("Failed to login")
                }
            }
    }


    interface View{
        fun sendToast(message: String)
    }
}