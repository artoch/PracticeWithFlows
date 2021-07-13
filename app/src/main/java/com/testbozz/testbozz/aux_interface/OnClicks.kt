package com.testbozz.testbozz.aux_interface

interface AdapterOnClick<T> {
    fun adapterOnClick(item:T)
}

interface CUD<T>{
    companion object {
        const val DELETE = 0
        const val SAVE = 1
    }
    fun onCUD(item:T, type:Int)
}
