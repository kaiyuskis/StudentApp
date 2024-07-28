package com.example.studentapp.ui.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentapp.R;
import com.example.studentapp.databinding.FragmentTodoBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoFragment extends Fragment {

    private FragmentTodoBinding binding;
    private final List<TodoAdapter.ToDoItem> todoList = new ArrayList<>();
    private TodoAdapter adapter;
    private Date selectedDeadline;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        try {
            setupRecyclerView();
            binding.addToDoButton.setOnClickListener(v -> showAddToDoDialog());
        } catch (Exception e) {
            Log.e("ToDoFragment", "Error initializing ToDoFragment", e);
        }

        return view;
    }

    private void setupRecyclerView() {
        try {
            adapter = new TodoAdapter();
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
            adapter.setToDoList(todoList);
        } catch (Exception e) {
            Log.e("ToDoFragment", "Error setting up RecyclerView", e);
        }
    }

    private void showAddToDoDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_add_todo, null);
            builder.setView(dialogView);

            EditText todoTitle = dialogView.findViewById(R.id.todoTitle);
            EditText todoDescription = dialogView.findViewById(R.id.todoDescription);
            dialogView.findViewById(R.id.selectDeadlineButton).setOnClickListener(v -> showDatePickerDialog(todoDescription));

            dialogView.findViewById(R.id.saveButton).setOnClickListener(v -> {
                String title = todoTitle.getText().toString();
                String description = todoDescription.getText().toString();

                if (!title.isEmpty() && !description.isEmpty() && selectedDeadline != null) {
                    addToDoItem(title, description, selectedDeadline);
                    Toast.makeText(getContext(), "ToDo added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Please enter title, description, and select a deadline", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            Log.e("ToDoFragment", "Error showing Add ToDo dialog", e);
        }
    }

    private void showDatePickerDialog(EditText todoDescription) {
        try {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                selectedDeadline = selectedDate.getTime();
                todoDescription.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDeadline));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } catch (Exception e) {
            Log.e("ToDoFragment", "Error showing DatePicker dialog", e);
        }
    }

    private void addToDoItem(String title, String description, Date deadline) {
        try {
            todoList.add(new TodoAdapter.ToDoItem(title, description, deadline));
            adapter.setToDoList(todoList);
        } catch (Exception e) {
            Log.e("ToDoFragment", "Error adding ToDo item", e);
        }
    }
}
