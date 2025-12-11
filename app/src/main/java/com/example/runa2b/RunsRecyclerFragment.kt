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

    //    interface RecyclerFragListener {
//        fun addButtonClicked()
//        fun itemClick(id: Int)
//    }
    lateinit var binding: FragmentRunsRecyclerBinding

    lateinit var adapter: EmployeeRecyclerAdapter

//    var ownerActivity: RecyclerFragListener? = null

    lateinit var viewModel : EmployeeViewModel

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        try {
//            ownerActivity = context as RecyclerFragListener
//            Log.i("SOUT", "RecyclerFragListener implemented successfully")
//
//        } catch (e: Exception) {
//            Log.e("SOUT", "FAILED! RecyclerFragListener NOT implemented: ${e.message}")
//        }
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[EmployeeViewModel::class.java]

        adapter = EmployeeRecyclerAdapter({ employee ->
            showDeleteDialog(employee)

        }, { employee ->
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frag_cont_main, EditFragment.newInstance(employee.id))
                addToBackStack(null)
                commit()
            }
        }) // om sista parametern i en metod är en lambda kan man lägga den efter parentes
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

        binding.recycleViewEmployees.layoutManager = LinearLayoutManager(requireContext())  // eller view.context
        binding.recycleViewEmployees.addItemDecoration(SpaceItemDecoration(24))
        binding.recycleViewEmployees.adapter = adapter

        binding.fabAdd.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frag_cont_main, EditFragment.newInstance(null))
                addToBackStack(null)
                commit()
            }
        }

        viewModel.employee.observe(viewLifecycleOwner) {
                list ->
            adapter.submitList(list)
        }

    }

    fun showDeleteDialog(employee: Employee) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Delete Employee")
            .setMessage("Do you want to delete: ${employee.name}?")
            .setPositiveButton("Yes"){_,_ ->
                viewModel.deleteEmployee(employee.id)
            }.setNegativeButton("Cancel", null).show()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}