package com.ardnn.githubuserv2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ardnn.githubuserv2.R
import com.ardnn.githubuserv2.databinding.FragmentUserFollBinding

class UserFollFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) =
            UserFollFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    private var _binding: FragmentUserFollBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         _binding = FragmentUserFollBinding.inflate(inflater, container, false)

        // get args
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        binding.tvTest.text = "Deh kenapa dle ini viewpager kodong, nda mau skali dia isi sisa layoutnya fak $index"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
    }
}