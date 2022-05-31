package com.mobile.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.databinding.FragmentHomeBinding
import com.mobile.test.model.Chapter
import com.mobile.test.model.Level

// the fragment initialization parameters
private const val ARG_PARAM_TOKEN = "Token"
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var token: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(ARG_PARAM_TOKEN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*_binding?.goToChapterMock?.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_chapterFragment)
        }*/



        // En el futuro, sacar 'levels' de la API
        val levels = mutableListOf(
            Level(
                chapters = mutableListOf(
                    Chapter(
                        name = "Capitulo 1 - View Binding",
                        image = "chapter_1",
                        url = "https://devexperto.com/view-binding/",
                        status = "pending",
                        questions = mutableListOf()
                    ),
                    Chapter(
                        name = "Capitulo 2",
                        image = "chapter_2",
                        url = "https://devexperto.com/view-binding/",
                        status = "pending",
                        questions = mutableListOf()
                    ),
                ),
            ),
            Level(
                chapters = mutableListOf(
                    Chapter(
                        name = "Capitulo 3",
                        image = "chapter_3",
                        url = "https://devexperto.com/view-binding/",
                        status = "pending",
                        questions = mutableListOf()
                    ),
                    Chapter(
                        name = "Capitulo 4",
                        image = "chapter_2",
                        url = "https://devexperto.com/view-binding/",
                        status = "pending",
                        questions = mutableListOf()
                    ),
                    Chapter(
                        name = "Capitulo 5",
                        image = "chapter_3",
                        url = "https://devexperto.com/view-binding/",
                        status = "pending",
                        questions = mutableListOf()
                    )
                ),
            )
        )

        recyclerView = binding.levelsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = LevelsAdapter(levels)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(token: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_TOKEN, token)
                }
            }
    }
}