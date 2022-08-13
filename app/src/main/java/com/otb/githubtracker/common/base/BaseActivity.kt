package com.otb.githubtracker.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by Mohit Rajput on 13/08/22.
 * Base class for all activities of app which mainly fulfill the purpose of creating and destroying
 * binding.
 * Child activity need to implement inflateLayout() with specific binding.
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView()
    }

    abstract fun setupView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}