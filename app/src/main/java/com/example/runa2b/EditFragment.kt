package com.example.runa2b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runa2b.databinding.FragmentEditBinding

private const val ARG_ID = "id"

class EditFragment : Fragment() {
    private var runId: String? = null

    lateinit var viewModel: RunViewModel

    lateinit var binding: FragmentEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[RunViewModel::class.java]

        arguments?.let {
            runId = it.getString(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (runId != null && runId != null) {
            loadTextFields(runId!!)
        }

        binding.cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.saveButton.setOnClickListener {
            val distance = binding.etDistance.text.toString().replace(",",".").toDoubleOrNull() ?: 0.0
            val name = binding.etName.text.toString()
            val date = binding.etDate.text.toString()

            if (runId == null) {
                viewModel.addRun(distance, name, date)
            } else if (runId != null) {
                viewModel.updateRun(runId!!, distance, name, date)
            }

            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    fun loadTextFields(id: String) {
        val run = viewModel.getRun(id)

        binding.etDistance.setText(run?.distance.toString())
        binding.etName.setText(run?.name)
        binding.etDate.setText(run?.date)

        binding.tvTitle.text = "Edit Run"
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String?) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}