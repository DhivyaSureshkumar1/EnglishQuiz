package com.education.englishquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.education.englishquiz.databinding.FragmentWelcomeBinding
import com.education.englishquiz.model.User


class WelcomeFragment : Fragment() {

    private var binding: FragmentWelcomeBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            welcomeFragment = this@WelcomeFragment        }
    }

    fun start() {
        val enteredName = binding?.nameEt?.text.toString()
        val enteredGrade = binding?.gradeEt?.text?.toString()

        if(binding!!.nameEt.text.isEmpty() && binding!!.gradeEt.text.isEmpty()){
            Toast.makeText(activity, "Enter name and grade.",Toast.LENGTH_SHORT).show()

        }else{
            val user = User(enteredName,enteredGrade!!.toInt())
            val bundle = bundleOf("user" to user)
            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment, bundle)
        }
        //grade = enteredGrade.toInt()

    }

    fun clear(){
        binding?.nameEt?.text = null
        binding?.gradeEt?.text = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}