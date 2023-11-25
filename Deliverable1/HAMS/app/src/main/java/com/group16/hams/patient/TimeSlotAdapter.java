package com.group16.hams.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group16.hams.R;
import com.group16.hams.RecyclerViewInterface;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    private ArrayList<TimeSlotHolder> timeSlotHolders;
    public TimeSlotAdapter(Context context, ArrayList<TimeSlotHolder> timeSlotHolders, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.timeSlotHolders = timeSlotHolders;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public TimeSlotAdapter.TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_timeslot, parent, false);

        return new TimeSlotViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.TimeSlotViewHolder holder, int position) {
        holder.tvAppointmentDate.setText(timeSlotHolders.get(position).getAppointmentDate());
        holder.tvAppointmentStartTime.setText(timeSlotHolders.get(position).getAppointmentStartTime());
        holder.tvAppointmentEndTime.setText(timeSlotHolders.get(position).getAppointmentEndTime());
        holder.tvAppointmentDoctorName.setText(timeSlotHolders.get(position)
                .getAppointmentDoctorName());
    }

    @Override
    public int getItemCount() {
        return timeSlotHolders.size();
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppointmentDate, tvAppointmentStartTime, tvAppointmentEndTime, tvAppointmentDoctorName;
        int type;
        public TimeSlotViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvAppointmentDate = itemView.findViewById(R.id.textAppointmentDate);
            tvAppointmentStartTime = itemView.findViewById(R.id.textAppointmentStartTime);
            tvAppointmentEndTime = itemView.findViewById(R.id.textAppointmentEndTime);
            tvAppointmentDoctorName = itemView.findViewById(R.id.textAppointmentDoctorName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(type, position);
                    }
                }
            });
        }
    }
}