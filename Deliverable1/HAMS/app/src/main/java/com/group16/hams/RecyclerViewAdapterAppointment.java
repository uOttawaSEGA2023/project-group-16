package com.group16.hams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterAppointment extends RecyclerView.Adapter<RecyclerViewAdapterAppointment.MyViewHolder> {
    Context context;
    ArrayList<RecyclerViewHolderAppointment> appointmentHolders;

    public RecyclerViewAdapterAppointment(Context context,
                                          ArrayList<RecyclerViewHolderAppointment> appointmentHolders) {
        this.context = context;
        this.appointmentHolders = appointmentHolders;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterAppointment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_appointment, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAppointment.MyViewHolder holder, int position) {
        holder.tvAppointmentDate.setText(appointmentHolders.get(position).getAppointmentDate());
        holder.tvAppointmentTime.setText(appointmentHolders.get(position).getAppointmentTime());
        holder.tvAppointmentPatientName.setText(appointmentHolders.get(position)
                .getAppointmentPatientName());
        holder.tvAppointmentApproval.setText(appointmentHolders.get(position)
                .getAppointmentApproval());

    }

    @Override
    public int getItemCount() {
        return appointmentHolders.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppointmentDate, tvAppointmentTime, tvAppointmentPatientName,
                tvAppointmentApproval;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAppointmentDate = itemView.findViewById(R.id.textAppointmentDate);
            tvAppointmentTime = itemView.findViewById(R.id.textAppointmentTime);
            tvAppointmentPatientName = itemView.findViewById(R.id.textAppointmentPatientName);
            tvAppointmentApproval = itemView.findViewById(R.id.textAppointmentApproval);
        }
    }
}
