package com.education.englishquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.englishquiz.adapter.QuestionAdapter
import com.education.englishquiz.databinding.FragmentSummeryBinding
import com.education.englishquiz.model.Questions

@Suppress("DEPRECATION")
class SummeryFragment : Fragment() {


    private var binding: FragmentSummeryBinding? = null
    private lateinit var adapter: QuestionAdapter
    private lateinit var recyclerView: RecyclerView
    private var questionList = listOf<Questions>()


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            questionList = bundle.getParcelableArrayList("quesList")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentSummeryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        recyclerView = binding?.summRecycle!!
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = QuestionAdapter()
        recyclerView.adapter = adapter

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            summeryFragment = this@SummeryFragment
        }
        questionList.let {
            adapter.setQuestions(it)
        }
    }
    fun startAgain(){
        findNavController().navigate(R.id.action_summeryFragment_to_welcomeFragment2)
    }

}