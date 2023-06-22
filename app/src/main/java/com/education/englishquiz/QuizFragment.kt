package com.education.englishquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.education.englishquiz.databinding.FragmentQuizBinding
import com.education.englishquiz.model.Questions
import com.education.englishquiz.model.QuizDatabaseHelper
import com.education.englishquiz.model.User


@Suppress("DEPRECATION")
class QuizFragment : Fragment() {


    private var binding: FragmentQuizBinding? = null
    private var questionList = listOf<Questions>()
    private var questionCount = 0
    private var next = "Next"
    private var finish = "Finish"
    private var answered = false
    private lateinit var currentQuestion: Questions
    private lateinit var user: User
    private var grade: Int? = 0
    private var questionTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getting user details from WelcomeFragment
        arguments?.let { bundle ->
            user = bundle.getParcelable("user")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentQuizBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            quizFragment = this@QuizFragment
        }
        // getting list of questions from SQLiteDatabase
        val context = requireContext()
        val quizDatabaseHelper = QuizDatabaseHelper(context)
        grade = user.grade
        if (savedInstanceState == null) {
            var questionList1 = quizDatabaseHelper.getAllQuestions(grade)
            questionList = questionList1.shuffled()
            showNextQuestion()// show first question
            checkCorrectAnswer()// checking answer and displaying correct answer for first question

        } else {
            currentQuestion = questionList[questionCount - 1]
        }
        binding?.startBtn?.setOnClickListener { startQuiz()}
        binding?.cleatBtn?.setOnClickListener{binding?.radioGroup?.clearCheck()}

    }

    private fun startQuiz() {
        binding?.startBtn?.text = next
        showNextQuestion()
    }

    private fun showNextQuestion() {
        binding?.radioGroup?.clearCheck()
        binding?.tvShowAnswer?.text = ""

        if (questionCount < 10) {
            val radioGroup : RadioGroup? = binding?.radioGroup
            for (i in 0 until 4) {
                val view = radioGroup?.getChildAt(i)
                if (view is RadioButton) {
                    view.isEnabled = true
                }
            }
            currentQuestion = questionList[questionCount]
            // setting questions and options in corresponding views
            binding?.tvQuestion?.text = currentQuestion.ques
            binding?.radioButton1?.text = currentQuestion.opt1
            binding?.radioButton2?.text = currentQuestion.opt2
            binding?.radioButton3?.text = currentQuestion.opt3
            binding?.radioButton4?.text = currentQuestion.opt4
            val s1 = currentQuestion.topic
            val s2 = "In the given sentence find $s1"
            binding?.tvInstruction?.text = s2
            questionCount++
            val s3 = "$questionCount/10"
            binding?.tvQuesNo?.text = s3
            val s4 = "10:00"
            binding?.tvTimer?.text = s4
            answered = false
            //setting timer for ten seconds
            startTimer(questionCount)
        } else {
            val bundle = Bundle()
            bundle.putParcelableArrayList("quesList", ArrayList(questionList))
            // navigating to SummeryFragment along with argument user
            findNavController().navigate(R.id.action_quizFragment_to_summeryFragment, bundle)
        }

    }
    private fun startTimer(questionIndex: Int) {
        questionTimer?.cancel()
        questionTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val s1 = "Timer = $secondsRemaining:00"
                binding?.tvTimer?.text=s1
                checkCorrectAnswer()
            }

            override fun onFinish() {
                val s1 = getCorrectAnswer(questionList[questionIndex])
                val s2 = "Time out. Answer : $s1"
                binding?.tvShowAnswer?.text= s2
                // disabling radio group view after ten seconds
                val radioGroup : RadioGroup? = binding?.radioGroup
                for (i in 0 until 4) {
                    val view = radioGroup?.getChildAt(i)
                    if (view is RadioButton) {
                        view.isEnabled = false
                    }
                }

            }
        }.start()
    }

    private fun checkCorrectAnswer() {
        answered = true
        val ans = currentQuestion.correct_opt
        val answer = getCorrectAnswer(currentQuestion)
        val correctAnswer = "CORRECT ANSWER : $answer"
        val correct = "Your answer is correct!!!"

        binding?.radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            questionTimer?.cancel()

            when (checkedId) {
                R.id.radio_button1 -> {
                    if (ans == 1) {
                        binding?.tvShowAnswer?.text = correct
                    } else {
                        binding?.tvShowAnswer?.text = correctAnswer
                    }
                }

                R.id.radio_button2 -> {
                    if (ans == 2) {
                        binding?.tvShowAnswer?.text = correct
                    } else {
                        binding?.tvShowAnswer?.text = correctAnswer
                    }
                }

                R.id.radio_button3 -> {
                    if (ans == 3) {
                        binding?.tvShowAnswer?.text = correct
                    } else {
                        binding?.tvShowAnswer?.text = correctAnswer
                    }
                }

                R.id.radio_button4 -> {
                    if (ans == 4) {
                        binding?.tvShowAnswer?.text = correct
                    } else {
                        binding?.tvShowAnswer?.text = correctAnswer
                    }
                }
            }
        }
        if (questionCount == 10) {
            binding?.startBtn?.text = finish
        }

    }

    private fun getCorrectAnswer(currentQuestion: Questions): String {
        var correctAns =""
        when(currentQuestion.correct_opt){
            1-> correctAns = currentQuestion.opt1
            2-> correctAns = currentQuestion.opt2
            3-> correctAns = currentQuestion.opt3
            4-> correctAns = currentQuestion.opt4
        }
        return correctAns
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}