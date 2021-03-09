package com.pme.mpe.activities.TaskActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.ui.block.BlockFragment;

import java.time.LocalDate;


public class NewTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskDescription;
    //private Date taskDate;
    private View taskDate;
    //private int taskDuration;
    private int taskDuration;
    //private Spinner taskCategory;
    private long taskCategoryId;
    private ImageButton predefinedColor;
    private SwitchCompat isFixed;
    private Button saveTaskBtn;
    private NewTaskActivityViewModel newTaskActivityViewModel;
    private LocalDate taskDeadline;





    private final View.OnClickListener saveTaskClickListener = v -> {

        if (v.getId() == R.id.save_task) {
            Task newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(),  taskCategoryId, taskDuration, taskDeadline);
            newTaskActivityViewModel.saveTasks(newTask);
            Intent taskIntent = new Intent(getApplicationContext(), BlockFragment.class);
            startActivity(taskIntent);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_task);
        newTaskActivityViewModel = new ViewModelProvider(this).get(NewTaskActivityViewModel.class);
        taskName = findViewById(R.id.task_name_input);
        taskDescription = findViewById(R.id.task_descrip_input);
        //taskCategoryId =
        //taskDuration = findViewById(R.id.task_duration_select);
        taskDate = findViewById(R.id.task_date_select);
        Button saveTask = findViewById(R.id.save_task);

        // TODO: Wenn Duration int ist, kann ich keine view dazu machen? Wenn ich die duration als TextView mache,
        // TODO: verlangt er bei new Task ein int. CategoryID muss irgendwie übergeben werden

        saveTask.setOnClickListener(this.saveTaskClickListener);
    }

}







