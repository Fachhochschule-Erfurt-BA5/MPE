package com.pme.mpe.ui.block;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.pme.mpe.MainActivity;
import com.pme.mpe.R;
import com.pme.mpe.activities.TaskActivity.EditTaskActivity;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.home.HomeViewModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private final TasksPackageRepository tasksPackageRepository;
    private final TaskViewModel taskViewModel;

    TaskAdapter(List<Task> taskList, TasksPackageRepository tasksPackageRepository, TaskViewModel taskViewModel) {
        this.taskList = taskList;
        this.tasksPackageRepository = tasksPackageRepository;
        this.taskViewModel = taskViewModel;
    }

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
        taskViewHolder.taskDeadline.setText("Deadline: "+task.getDeadline());
        taskViewHolder.taskDuration.setText("Duration: "+task.getDuration()+"h");


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
        taskViewHolder.taskCard.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                taskViewHolder.updateLayout.setAlpha(1);
                taskViewHolder.updateLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, v.getContext().getResources().getDisplayMetrics());
                taskViewHolder.updateLayout.requestLayout();
                taskViewHolder.contentLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 340, v.getContext().getResources().getDisplayMetrics());
                taskViewHolder.contentLayout.requestLayout();
                return true;
            }
        });
        taskViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskViewModel.deleteTask(task);
                Intent mainTaskIntent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(mainTaskIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView taskName;
        TextView taskDescription;
        AppCompatTextView taskDeadline;
        AppCompatTextView taskDuration;
        MaterialCardView taskCard;
        LinearLayout updateLayout;
        LinearLayoutCompat contentLayout;
        ImageButton deleteBtn;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (AppCompatTextView) itemView.findViewById(R.id.task_item_name);
            taskDescription = (TextView) itemView.findViewById(R.id.task_item_descrip);
            taskDeadline = (AppCompatTextView) itemView.findViewById(R.id.task_item_deadline);
            taskDuration = (AppCompatTextView) itemView.findViewById(R.id.task_item_duration);
            taskCard = (MaterialCardView) itemView.findViewById(R.id.task_item_card);
            updateLayout = (LinearLayout) itemView.findViewById(R.id.update_task_layout);
            contentLayout = (LinearLayoutCompat) itemView.findViewById(R.id.task_item_layout);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete_task_btn);
        }

    }
}

