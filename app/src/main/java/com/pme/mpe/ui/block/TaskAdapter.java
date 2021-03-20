package com.pme.mpe.ui.block;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;

    TaskAdapter(List<Task> taskList) { this.taskList = taskList; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder taskViewHolder, int i) {
        Task task = taskList.get(i);
        taskViewHolder.taskName.setText(task.getName());
        taskViewHolder.taskDescription.setText(task.getDescription());
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView taskName;
        TextView taskDescription;
        TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (AppCompatTextView) itemView.findViewById(R.id.task_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.task_item_descrip);
        }

    }
}

