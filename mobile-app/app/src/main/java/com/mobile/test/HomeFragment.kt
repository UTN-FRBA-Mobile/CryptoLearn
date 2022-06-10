package com.mobile.test

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.api.*
import com.mobile.test.databinding.FragmentHomeBinding
import com.mobile.test.model.Chapter
import com.mobile.test.model.Level
import com.mobile.test.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// the fragment initialization parameters
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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

        var levels: List<Level> = listOf(Level(mutableListOf(Chapter("", "", "", "", mutableListOf(Question("", mutableListOf(""), 1))))));

        sessionManager = SessionManager.getInstance(requireContext())
        RetrofitClient.service.getLevels(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object: Callback<LevelsResponse> {
                override fun onResponse(call: Call<LevelsResponse>, response: Response<LevelsResponse>) {
                    if (response.isSuccessful) {
                        levels = response.body()?.response!!
                        recyclerView = binding.levelsRecyclerView.apply {
                            layoutManager = LinearLayoutManager(this.context)
                            adapter = LevelsAdapter(levels)
                        }
                    } else {
                        val toast = Toast.makeText(context, resources.getString(R.string.bad_get_levels), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                        toast.show()
                    }
                }
                override fun onFailure(call: Call<LevelsResponse>, error: Throwable) {
                    val toast = Toast.makeText(context, resources.getString(R.string.error_occurred), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                    toast.show()
                }
            })

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
        fun newInstance() =
            HomeFragment()
    }
}