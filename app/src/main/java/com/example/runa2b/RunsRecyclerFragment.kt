package com.example.runa2b

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runa2b.databinding.FragmentRunsRecyclerBinding

class RunsRecyclerFragment : Fragment() {

    lateinit var binding: FragmentRunsRecyclerBinding

    lateinit var adapter: RunRecyclerAdapter

    lateinit var viewModel : RunViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[RunViewModel::class.java]

        adapter = RunRecyclerAdapter({ run ->
            showDeleteDialog(run)

        }, { run ->
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frag_cont_main, EditFragment.newInstance(run.id))
                addToBackStack(null)
                commit()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRunsRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleViewRuns.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewRuns.addItemDecoration(SpaceItemDecoration(24))
        binding.recycleViewRuns.adapter = adapter

        binding.fabAdd.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frag_cont_main, EditFragment.newInstance(null))
                addToBackStack(null)
                commit()
            }
        }

        viewModel.run.observe(viewLifecycleOwner) {
                list ->
            adapter.submitList(list)
        }

    }

    fun showDeleteDialog(run: Run) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Delete run")
            .setMessage("Do you want to delete:\n${run.distance} km\n${run.name}\n${run.date}?")
            .setPositiveButton("Yes"){_,_ ->
                viewModel.deleteRun(run.id)
            }.setNegativeButton("Cancel", null).show()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}