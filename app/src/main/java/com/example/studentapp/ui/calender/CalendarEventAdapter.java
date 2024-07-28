package com.example.studentapp.ui.calender;

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

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

    private final List<CalendarEvent> events = new ArrayList<>();

    public void setEvents(List<CalendarEvent> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarEvent event = events.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.eventDate.setText(dateFormat.format(event.getDate()));
        holder.eventTitle.setText(event.getTitle());
        holder.eventDescription.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventDate;
        TextView eventTitle;
        TextView eventDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDescription = itemView.findViewById(R.id.eventDescription);
        }
    }

    public static class CalendarEvent {
        private final Date date;
        private final String title;
        private final String description;

        public CalendarEvent(Date date, String title, String description) {
            this.date = date;
            this.title = title;
            this.description = description;
        }

        public Date getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
