package com.pme.mpe.ui.block;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.R;
import com.pme.mpe.activities.BlockCategoryActivity.EditBlockCategoryActivity;
import com.pme.mpe.activities.TaskActivity.EditTaskActivity;
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
        taskViewHolder.taskCard.setCardBackgroundColor(Color.parseColor(task.getTaskColor()));

        taskViewHolder.taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTaskIntent = new Intent(v.getContext(), EditTaskActivity.class);
                editTaskIntent.putExtra("TaskID", (int) task.getId());
                editTaskIntent.putExtra("TaskName", task.getName());
                editTaskIntent.putExtra("TaskDescription", task.getDescription());
                editTaskIntent.putExtra("TaskDuration", task.getDuration());
                editTaskIntent.putExtra("TaskDeadline", task.getDeadline());
                editTaskIntent.putExtra("TaskColor", task.getTaskColor());
                editTaskIntent.putExtra("TaskCategoryBlock", (Parcelable) task.getCategoryBlock());
                editTaskIntent.putExtra("TaskCategory", (int) task.getT_categoryId());
                v.getContext().startActivity(editTaskIntent);
            }
        });
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView taskName;
        TextView taskDescription;
        MaterialCardView taskCard;
        TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (AppCompatTextView) itemView.findViewById(R.id.task_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.task_item_descrip);
            taskCard = (MaterialCardView) itemView.findViewById(R.id.task_item_card);

        }

    }
}

