package com.testbozz.testbozz.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.testbozz.testbozz.R
import com.testbozz.testbozz.adapter.CommentsAdapter
import com.testbozz.testbozz.databinding.DialogBCommentsBinding
import com.testbozz.testbozz.ui.view_model.PostViewModel
import com.testbozz.testbozz.utils.StateForFragments
import com.testbozz.testbozz.utils.linear

class CommentsDialogFragment(private val idx:Int): BottomSheetDialogFragment() {

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
    }
    lateinit var binding: DialogBCommentsBinding
    private lateinit var adapter : CommentsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_b_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogBCommentsBinding.bind(view)
        setupView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    private fun setupView(){
        binding.apply {
            adapter = CommentsAdapter(ArrayList())
            rvComments.layoutManager = linear(requireContext())
            rvComments.adapter = adapter
        }
        setupObserver()
    }

    private fun setupObserver(){
        vm.myPostComments.observe(requireActivity(), {
            adapter.setData(it)
        })
        vm.getPostCommentsByIdFlow(idx)
        vm.networkState.observe(requireActivity(), ::errorState)
    }

    private fun errorState(error: StateForFragments){
        if (error is StateForFragments.ErrorPostComents){
            Toast.makeText(requireContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}

