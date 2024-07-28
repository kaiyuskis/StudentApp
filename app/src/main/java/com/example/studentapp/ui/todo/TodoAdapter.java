package com.example.studentapp.ui.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private final List<ToDoItem> todoList = new ArrayList<>();

    public void setToDoList(List<ToDoItem> todoList) {
        this.todoList.clear();
        this.todoList.addAll(todoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoItem todoItem = todoList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.todoTitle.setText(todoItem.getTitle());
        holder.todoDescription.setText(todoItem.getDescription());
        holder.todoDeadline.setText(dateFormat.format(todoItem.getDeadline()));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView todoTitle;
        TextView todoDescription;
        TextView todoDeadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todoTitle);
            todoDescription = itemView.findViewById(R.id.todoDescription);
            todoDeadline = itemView.findViewById(R.id.todoDeadline);
        }
    }

    public static class ToDoItem {
        private final String title;
        private final String description;
        private final Date deadline;

        public ToDoItem(String title, String description, Date deadline) {
            this.title = title;
            this.description = description;
            this.deadline = deadline;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Date getDeadline() {
            return deadline;
        }
    }
}
