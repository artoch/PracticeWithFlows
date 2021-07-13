package com.testbozz.testbozz.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.testbozz.testbozz.R
import com.testbozz.testbozz.adapter.PostAdapter
import com.testbozz.testbozz.aux_interface.AdapterOnClick
import com.testbozz.testbozz.aux_interface.CUD
import com.testbozz.testbozz.data.models.PostItemEntity
import com.testbozz.testbozz.databinding.FragmentPostBinding
import com.testbozz.testbozz.ui.view_model.PostViewModel
import com.testbozz.testbozz.utils.StateForFragments
import com.testbozz.testbozz.utils.linear

class PostListFragment: Fragment(),
    AdapterOnClick<PostItemEntity>,
    CUD<PostItemEntity> {

    private lateinit var binding: FragmentPostBinding
    private lateinit var adapter : PostAdapter

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)
        setupView()
    }

    private fun setupView(){
        binding.apply {
            adapter = PostAdapter(ArrayList(), this@PostListFragment, this@PostListFragment, CUD.SAVE)
            rvPost.layoutManager = linear(requireContext())
            rvPost.adapter = adapter
        }
        setupObserver()
    }

    private fun setupObserver(){
        vm.clearData()
        vm.allMyPost.observe(requireActivity(), ::postState)
        vm.networkState.observe(requireActivity(), ::errorState)
        vm.getAllPostFlow()

    }

    private fun errorState(error: StateForFragments){
        when (error){
            is StateForFragments.ErrorListFragment -> {
                adapter.setType(CUD.DELETE)
                Toast.makeText(requireContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                vm.getMyCachePost()
            }
            is StateForFragments.Success -> {
                adapter.setType(CUD.SAVE)
            }
            else -> {}
        }
    }

    private fun postState(postItemEntity: List<PostItemEntity>){
        if (postItemEntity.isNotEmpty()){
            adapter.setData(postItemEntity)
        }
    }

    override fun adapterOnClick(item: PostItemEntity) {
        val action = PostListFragmentDirections.actionPostListFragmentToDetailsPostFragment(item.id.toInt())
        view?.findNavController()?.navigate(action)
    }

    override fun onCUD(item: PostItemEntity, type: Int) {
        when (type) {
            CUD.SAVE   -> {
                Toast.makeText(requireContext(), getString(R.string.save_post), Toast.LENGTH_SHORT).show()
                vm.saveToCachePost(item)
            }
            CUD.DELETE -> {
                Toast.makeText(requireContext(), getString(R.string.delete_post), Toast.LENGTH_SHORT).show()
                vm.deleteFromCachePost(item.id.toInt())
            }
        }
    }
}