package com.pme.mpe.ui.block;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.ui.category.CategoryAdapter;

import java.util.List;

public class TaskAdapter extends BaseAdapter { //extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>

    private List<Task> taskList;
    private BlockViewModel taskViewModel;

    public TaskAdapter(List<Task> taskList) {

        this.taskList = taskList;
    }

    /*@NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }*/

    /*@Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder taskViewHolder, int i) {
        Task task = taskList.get(i);
        taskViewHolder.taskName.setText(task.getName());
        taskViewHolder.taskDescription.setText(task.getDescription());
        taskViewHolder.taskDuration.setText(task.getDuration());

    }*/


    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder { //extends RecyclerView.ViewHolder
        AppCompatTextView taskName;
        TextView taskDescription;
        TextView taskDuration;
        LinearLayout updateLayout;
        LinearLayoutCompat contentLayout;

        /*TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (AppCompatTextView) itemView.findViewById(R.id.task_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.task_descrip_input);
            taskDuration = (TextView) itemView.findViewById(R.id.task_duration_select);

        }*/

    }

    @Override
    public int getCount() {
        if (this.taskList != null && !this.taskList.isEmpty())
            return this.taskList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.taskList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new TaskViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.task_item, parent, false);

            viewHolder.taskName = (AppCompatTextView) convertView.findViewById(R.id.task_item_name);
            viewHolder.taskDescription = (TextView) convertView.findViewById(R.id.task_item_descrip);
            viewHolder.taskDuration = (TextView) convertView.findViewById(R.id.task_item_time);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (TaskAdapter.TaskViewHolder) convertView.getTag();
        }

        viewHolder.taskName.setText(taskList.get(position).getName());
        viewHolder.taskDescription.setText(taskList.get(position).getDescription());
        viewHolder.taskDuration.setText(taskList.get(position).getDuration());
        return convertView;
    }
}
