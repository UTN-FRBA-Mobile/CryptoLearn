package com.mobile.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mobile.test.databinding.FragmentQuestionBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.mobile.test.model.Chapter
import com.mobile.test.model.Question


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM_CHAPTER_DATA = "chapterData"


/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var chapterData: Chapter
    private lateinit var currentQuestion: Question
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var questionIndex: Int = 0
    private var questionAnswered: Boolean = false
    private var selectedAnswer: Int? = null//TODO juani: sacar
    private var selectedAnswerString: String? = null//TODO juani: sacar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chapterData = it.get(ARG_PARAM_CHAPTER_DATA) as Chapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = object : FlexboxLayoutManager(this.context) {
            override fun canScrollVertically(): Boolean {
                return false
            }

            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

        recyclerView = binding.questionOptionsRecyclerView.apply {
            layoutManager = manager.apply {
                justifyContent = JustifyContent.CENTER
                flexDirection = FlexDirection.COLUMN
                alignItems = AlignItems.CENTER
            }
        }

        // Carga los datos de la primer pregunta en la view
        this.showNextQuestion()

        binding.questionFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.questionOptionButton.setOnClickListener {
            Log.d("PRINT", "QUESTION ANSWERED: $questionAnswered")
            Log.d("PRINT", "ANSWER: $selectedAnswerString")
            if (binding.questionOptionButton.text == "NEXT") {
                // Carga los datos de la siguiente pregunta en la view
                Log.d("PRINT", "NEXT QUESTION")
                questionIndex++
                this.showNextQuestion()
            }
            if (selectedAnswerString !== null) {
                if (selectedAnswerString!!.lowercase() == chapterData.questions?.get(questionIndex)?.answer?.lowercase()) {
                    // la resp esta bien
                    checkAnswer(true)
                } else {
                    // la resp esta mal
                    checkAnswer(false)
                }
            } else {
                // no se selecciono, asi que no se hace nada
            }
        }
    }

    fun showNextQuestion() {
        if (questionIndex < chapterData.questions!!.count()) {
            chapterData.questions!![questionIndex].let {
                binding.questionTitle.text = it.questionTitle
                binding.questionDescription.text = it.questionDescription
                binding.questionTitle.text = it.questionTitle
                binding.questionDescription.text = it.questionDescription
                currentQuestion = it
                questionAnswered = false
                binding.questionOptionButton.text = "CHECK"

                recyclerView.adapter =
                    QuestionOptionsAdapter(it, OnClickListener { answer ->
                        var newAnswer =
                            chapterData.questions!![questionIndex].options.indexOf(answer)
                        selectedAnswer = newAnswer
                        selectedAnswerString = answer

                        Log.d("PRINT", "Selected answer 1: $answer, number $selectedAnswer")
                        changeAnswer(newAnswer)
                    })
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        } else {
            // TODO: que pasa si se terminan las preguntas
            findNavController().navigate(R.id.action_questionFragment_to_homeFragment)
        }
    }

    fun changeAnswer(newAnswer: Int) {
        var newQuestion = currentQuestion
        newQuestion.selectedAnswer = newAnswer
        currentQuestion = newQuestion
        Log.d("PRINT", "$newQuestion- newAnser:$newAnswer")
        recyclerView.adapter =
            QuestionOptionsAdapter(newQuestion, OnClickListener { answer ->
                Log.d("PRINT", "onClick answer: $answer")
                var newAnswer =
                    chapterData.questions!![questionIndex].options.indexOf(answer)
                selectedAnswer = newAnswer
                selectedAnswerString = answer
                Log.d("PRINT", "Selected answer 2: $answer, number $selectedAnswer")
                changeAnswer(newAnswer)
            })
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    fun checkAnswer(isCorrect: Boolean) {
        var listenerr: OnClickListener? = null
        var newQuestion = currentQuestion
        newQuestion.isCorrect = isCorrect
        currentQuestion = newQuestion
        questionAnswered = true
        if (isCorrect){
            binding.questionOptionButton.text = "NEXT"
        } else {
            listenerr = OnClickListener { answer ->
                Log.d("PRINT", "onClick answer: $answer")
                var newAnswer =
                    chapterData.questions!![questionIndex].options.indexOf(answer)
                selectedAnswer = newAnswer
                selectedAnswerString = answer
                Log.d("PRINT", "Selected answer 2: $answer, number $selectedAnswer")
                changeAnswer(newAnswer)
            }
        }
        recyclerView.adapter = QuestionOptionsAdapter(newQuestion, listenerr)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_CHAPTER_DATA, chapterData)
                }
            }
    }
}