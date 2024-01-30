package com.example.gshop.firestore

import android.app.Activity
import android.util.Log
import com.example.gshop.activities.LoginActivity
import com.example.gshop.activities.RegisterActivity
import com.example.gshop.models.User
import com.example.gshop.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFirestore=FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity,userInfo: User){
        // register new user in firebase firestore

        mFirestore.collection(Constants.USERS)
            .document(userInfo.id)
                // merge userInfo for later replacing fields
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user",
                    e
                )
            }
    }

    fun getCurrentUserID():String{
        val currentUser=FirebaseAuth.getInstance().currentUser
        var currentUserID=""
        if( currentUser != null){
            currentUserID=currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity){
        // collection name where we want to get data from
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document->
                Log.i(
                    activity.javaClass.simpleName,
                    document.toString()
                )
                //getting document snapshot which is converted to data model object
                val user=document.toObject(User::class.java)!!
                when(activity){
                    is LoginActivity->{
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e->

            }
    }

}