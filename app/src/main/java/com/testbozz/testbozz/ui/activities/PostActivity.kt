package com.testbozz.testbozz.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.testbozz.testbozz.databinding.ActivityMainBinding
import com.testbozz.testbozz.ui.view_model.PostViewModel
import com.testbozz.testbozz.utils.StateForFragments
import com.testbozz.testbozz.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {

    private val vm: PostViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navController = getNavController(binding.navHostFragmentContainer.id)
        }
        vm.networkStateFlow()
        vm.networkState.observe(this, ::networkState)
    }

    override fun onBackPressed() {

    }

    private fun networkState(stateNetwork: StateForFragments){
        binding.apply {
            when (stateNetwork){
                StateForFragments.Loading -> {
                    pgBar.visibility = View.VISIBLE
                }
                else -> {
                    pgBar.visibility = View.GONE
                }
            }
        }

    }
}