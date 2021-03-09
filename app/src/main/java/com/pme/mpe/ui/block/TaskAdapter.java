package com.pme.mpe.ui.block;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.category.CategoryAdapter;

import java.util.List;

public class TaskAdapter extends BaseAdapter { //extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>

    private final Context mContext;
    private List<Task> taskList;
    private BlockViewModel taskViewModel;
    private final TasksPackageRepository tasksPackageRepository;

    public TaskAdapter(Context mContext, List<Task> taskList, TasksPackageRepository tasksPackageRepository) {
        this.mContext = mContext;
        this.taskList = taskList;
        this.tasksPackageRepository = tasksPackageRepository;
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
        AppCompatTextView taskDescription;
        AppCompatTextView taskDuration;

        MaterialCardView taskElement;
        ImageButton deleteBtn;
        ImageButton editBtn;
        LinearLayout updateLayout;
        LinearLayout contentLayout;

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
            viewHolder.taskDescription = (AppCompatTextView) convertView.findViewById(R.id.task_item_descrip);
            viewHolder.taskDuration = (AppCompatTextView) convertView.findViewById(R.id.task_item_time);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.delete_task_btn);
            viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.edit_task_btn);
            viewHolder.contentLayout = convertView.findViewById(R.id.task_item_layout);
            viewHolder.updateLayout = convertView.findViewById(R.id.update_task_layout);
            viewHolder.taskElement = (MaterialCardView) convertView.findViewById(R.id.task_item_card);

            viewHolder.taskElement.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewHolder.updateLayout.setAlpha(1);
                    viewHolder.updateLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, parent.getContext().getResources().getDisplayMetrics());
                    viewHolder.updateLayout.requestLayout();
                    viewHolder.contentLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 115, parent.getContext().getResources().getDisplayMetrics());
                    viewHolder.contentLayout.requestLayout();
                    return true;
                }
            });


            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (TaskAdapter.TaskViewHolder) convertView.getTag();
        }

        viewHolder.taskName.setText(taskList.get(position).getName());
        viewHolder.taskDescription.setText(taskList.get(position).getDescription());
        viewHolder.taskDuration.setText(taskList.get(position).getDuration());
        return convertView;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }
}
