package com.mobile.test

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mobile.test.databinding.FragmentQuestionBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.mobile.test.api.RetrofitClient
import com.mobile.test.api.SessionManager
import com.mobile.test.api.SignupRequest
import com.mobile.test.api.UpdateChapterStatusRequest
import com.mobile.test.model.Chapter
import com.mobile.test.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    private var badQuestion: MutableList<Question> = mutableListOf()
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var questionIndex: Int = 0
    private var questionAnswered: Boolean = false
    private var selectedAnswer: Int? = null
    private var selectedAnswerString: String = ""
    private lateinit var sessionManager: SessionManager


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
        chapterData.questions?.let { badQuestion.addAll(it) }

        binding.questionFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.questionOptionButton.setOnClickListener {
            Log.d("PRINT", "QUESTION ANSWERED: $questionAnswered")
            Log.d("PRINT", "rta: $selectedAnswer")
            if (!questionAnswered) {
                if (selectedAnswer !== null) {
                    if (questionIndex < chapterData.questions?.size!!){
                        Log.d("PRINT", "correct: ${chapterData.questions?.get(questionIndex)?.answer}")
                        Log.d("PRINT", "rta: $selectedAnswerString")
                        if (selectedAnswerString.equals(chapterData.questions?.get(questionIndex)?.answer, ignoreCase = true)) {
                            // la resp esta bien
                            checkAnswer(true)
                        } else {
                            // la resp esta mal
                            checkAnswer(false)
                            badQuestion.add(currentQuestion.copy(currentQuestion.questionTitle, currentQuestion.questionDescription, currentQuestion.options,null,currentQuestion.answer,null,null))
                        }
                    } else {
                        if (selectedAnswerString.equals(badQuestion?.get(questionIndex)?.answer, ignoreCase = true)) {
                            // la resp esta bien
                            checkAnswer(true)
                        } else {
                            // la resp esta mal
                            checkAnswer(false)
                            badQuestion.add(currentQuestion.copy(currentQuestion.questionTitle, currentQuestion.questionDescription, currentQuestion.options,null,currentQuestion.answer,null,null))
                        }
                    }

                } else {
                    // no se selecciono, asi que no se hace nada
                }
            } else {
                // Carga los datos de la siguiente pregunta en la view
                if (binding.questionOptionButton.text.equals("PROXIMA PREGUNTA")) {
                    Log.d("PRINT", "NEXT QUESTION")
                    selectedAnswer = null
                    selectedAnswerString = ""
                    questionIndex++
                    this.showNextQuestion()
                }
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
                binding.questionOptionButton.text = "VALIDAR"

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
            if (questionIndex < badQuestion!!.count()) {
                badQuestion!![questionIndex].let {
                    binding.questionTitle.text = it.questionTitle
                    binding.questionDescription.text = it.questionDescription
                    binding.questionTitle.text = it.questionTitle
                    binding.questionDescription.text = it.questionDescription
                    currentQuestion = it
                    questionAnswered = false
                    binding.questionOptionButton.text = "VALIDAR"

                    recyclerView.adapter =
                        QuestionOptionsAdapter(it, OnClickListener { answer ->
                            var newAnswer =
                                badQuestion!![questionIndex].options.indexOf(answer)
                            selectedAnswer = newAnswer
                            selectedAnswerString = answer

                            Log.d("PRINT", "Selected answer 1: $answer, number $selectedAnswer")
                            changeAnswer(newAnswer)
                        })
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
            } else {
                completeChapter()
                findNavController().navigate(R.id.action_questionFragment_to_homeFragment)
            }
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
        binding.questionOptionButton.text = "PROXIMA PREGUNTA"
        var newQuestion = currentQuestion
        newQuestion.isCorrect = isCorrect
        currentQuestion = newQuestion
        questionAnswered = true
        recyclerView.adapter = QuestionOptionsAdapter(newQuestion, null)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun completeChapter() {
        sessionManager = SessionManager.getInstance(requireContext())
        RetrofitClient.service.updateChapterStatus(token = "Bearer ${sessionManager.fetchAuthToken()}", chapterData.levelId,chapterData.id, UpdateChapterStatusRequest("done"))
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    //TODO success message
                }
                override fun onFailure(
                    call: Call<String>,
                    error: Throwable
                ) {
                    //TODO error message
                }
            })
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