package com.testbozz.testbozz.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager

fun linear(context: Context) = LinearLayoutManager(context)

fun AppCompatActivity.getNavController(id:Int): NavController = (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
