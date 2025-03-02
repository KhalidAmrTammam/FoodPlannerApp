package com.iti.java.foodplannerbykhalidamr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DayViewHolder> {
    private final List<LocalDate> dates;
    private final PublishSubject<LocalDate> clickSubject = PublishSubject.create();

    public DaysAdapter(List<LocalDate> dates) {
        this.dates = dates;
    }

    public Observable<LocalDate> getDayClicks() {
        return clickSubject.hide();
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        LocalDate date = dates.get(position);
        holder.bind(date);

        holder.itemView.setOnClickListener(v ->
                clickSubject.onNext(date)
        );
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayName;
        private final TextView dateText;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            dateText = itemView.findViewById(R.id.date);
        }

        public void bind(LocalDate date) {
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d");

            dayName.setText(dayFormatter.format(date));
            dateText.setText(dateFormatter.format(date));
        }
    }
}