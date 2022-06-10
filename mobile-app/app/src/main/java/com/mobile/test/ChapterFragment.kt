package com.mobile.test

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.test.databinding.FragmentChapterBinding
import com.mobile.test.model.Chapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM_CHAPTER_DATA = "chapterData"

private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
/**
 * A simple [Fragment] subclass.
 * Use the [ChapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChapterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var chapterData: Chapter? = null
    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentChapterBinding.inflate(inflater,container, false)
        binding.chapterTitle = chapterData?.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.goToQuestion?.setOnClickListener{
            findNavController().navigate(R.id.action_chapterFragment_to_questionFragment)
        }

        // Set up WebView
        binding.chapterWebView.settings.loadsImagesAutomatically = true
        binding.chapterWebView.settings.javaScriptEnabled = true
        binding.chapterWebView.settings.domStorageEnabled = true
        binding.chapterWebView.settings.builtInZoomControls = true
        binding.chapterWebView.settings.setSupportZoom(true)
        binding.chapterWebView.webViewClient = WebViewClient()
        binding.chapterWebView.settings.userAgentString = USER_AGENT;
        if (chapterData?.url?.isNotEmpty() == true) {
            binding.chapterWebView.loadUrl(chapterData!!.url!!)
        } else {
            val unencodedHtml =
                "<html><body> No se pudo cargar el capitulo porque no se econtro ninguna URL para mostrar </body></html>";
            val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
            binding.chapterWebView.loadData(encodedHtml, "text/html", "base64")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChapterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(chapterData: Chapter) =
            ChapterFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_CHAPTER_DATA, chapterData)

                }
            }
    }
}