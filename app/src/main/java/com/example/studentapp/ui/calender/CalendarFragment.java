package com.example.studentapp.ui.calender;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentapp.R;
import com.example.studentapp.databinding.FragmentCalendarBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private final List<WeekViewAdapter.CalendarEvent> events = new ArrayList<WeekViewAdapter.CalendarEvent>();
    private WeekViewAdapter weekViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupRecyclerView();

        binding.addEventButton.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void setupRecyclerView() {
        weekViewAdapter = new WeekViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(weekViewAdapter);
        weekViewAdapter.setEvents(events);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            showAddEventDialog(selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showAddEventDialog(Calendar selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_event, null);
        builder.setView(dialogView);

        EditText eventTitle = dialogView.findViewById(R.id.eventTitle);
        EditText eventDescription = dialogView.findViewById(R.id.eventDescription);
        TimePicker eventTimePicker = dialogView.findViewById(R.id.eventTimePicker);
        eventTimePicker.setIs24HourView(true);

        dialogView.findViewById(R.id.saveButton).setOnClickListener(v -> {
            String title = eventTitle.getText().toString();
            String description = eventDescription.getText().toString();
            int hour = eventTimePicker.getHour();
            int minute = eventTimePicker.getMinute();

            if (!title.isEmpty() && !description.isEmpty()) {
                selectedDate.set(Calendar.HOUR_OF_DAY, hour);
                selectedDate.set(Calendar.MINUTE, minute);
                addEvent(selectedDate.getTime(), title, description);
                Toast.makeText(getContext(), "Event added on: " + selectedDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter both title and description", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addEvent(Date date, String title, String description) {
        events.add(new WeekViewAdapter.CalendarEvent(date, title, description));
        weekViewAdapter.setEvents(events);
    }
}
