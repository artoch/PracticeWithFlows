package com.testbozz.testbozz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.testbozz.testbozz.R
import com.testbozz.testbozz.aux_interface.CUD
import com.testbozz.testbozz.databinding.FragmentDetailPostBinding
import com.testbozz.testbozz.databinding.FragmentPostBinding
import com.testbozz.testbozz.ui.dialog.CommentsDialogFragment
import com.testbozz.testbozz.ui.view_model.PostViewModel
import com.testbozz.testbozz.utils.StateForFragments

class DetailsPostFragment: Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailPostBinding

    private val args: DetailsPostFragmentArgs by navArgs()

    private lateinit var dialog: CommentsDialogFragment

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailPostBinding.bind(view)
        setupView()
    }

    private fun setupView(){
        binding.apply {
            btnComments.setOnClickListener(this@DetailsPostFragment)
            vm.getPostByIdFlow(args.idPost)
        }
        setupBindings()
    }

    private fun setupBindings(){
        binding.apply {
            vm.myPost.observe(requireActivity(), {
                it?.let {
                    tvTitle.text = it.title
                    tvBody.text = it.body
                }
            })
            btnBack.setOnClickListener(this@DetailsPostFragment)
            btnComments.setOnClickListener(this@DetailsPostFragment)
            vm.networkState.observe(requireActivity(), ::errorState)
        }
    }

    private fun errorState(error: StateForFragments){
        when (error){
            is StateForFragments.ErrorPostIdFragment -> {
                Toast.makeText(requireContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                vm.getMyCachePostById(args.idPost)
            }
            is StateForFragments.Success -> {

            }
        }
    }

    private fun backToListPost(){
        val action = DetailsPostFragmentDirections.actionDetailsPostFragmentToPostListFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun openCommentsDialog(){
        if (::dialog.isInitialized){
            if (dialog.isVisible){
                return
            }else{
                showDialog()
            }
        }else{
            showDialog()
        }
    }

    private fun showDialog(){
        dialog = CommentsDialogFragment(args.idPost)
        dialog.show(parentFragmentManager, "SHOW")
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0!!.id){
                btnBack.id -> {
                    backToListPost()
                }
                btnComments.id -> openCommentsDialog()
            }
        }
    }

}