package com.example.runa2b


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runa2b.databinding.ListItemRunBinding

class RunRecyclerAdapter(
    val onItemLongClick: (Run) -> Unit,
    val onItemClick: (Run) -> Unit
) : RecyclerView.Adapter<RunRecyclerAdapter.RunViewHolder>() {

    private var runs = emptyList<Run>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RunViewHolder {
        val binding =
            ListItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RunViewHolder(binding)
    }

    fun submitList(runList: List<Run>) {
        runs = runList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RunViewHolder,
        position: Int
    ) {
        val run = runs[position]
        holder.binding.tvDistance.text = "${run.distance} km"
        holder.binding.tvName.text = run.name
        holder.binding.tvDate.text = run.date

        holder.binding.root.setOnClickListener {
            onItemClick(run)
        }

        holder.binding.root.setOnLongClickListener {
            onItemLongClick(run)
            true
        }
    }


    override fun getItemCount(): Int {

        return runs.size
    }

    inner class RunViewHolder(val binding: ListItemRunBinding) :
        RecyclerView.ViewHolder(binding.root)
}
