package com.testbozz.testbozz.utils

sealed class StateForFragments{
    object Success : StateForFragments()
    object Loading : StateForFragments()
    object ErrorListFragment   : StateForFragments()
    object ErrorPostIdFragment : StateForFragments()
    object ErrorPostComents    : StateForFragments()
}
