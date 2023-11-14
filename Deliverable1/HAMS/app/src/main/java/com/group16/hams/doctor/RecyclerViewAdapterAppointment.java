package com.group16.hams.doctor;

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

public class RecyclerViewAdapterAppointment extends RecyclerView.Adapter<RecyclerViewAdapterAppointment.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<RecyclerViewHolderAppointment> appointmentHolders;

    public RecyclerViewAdapterAppointment(Context context,
                                          ArrayList<RecyclerViewHolderAppointment> appointmentHolders,
                                          RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.appointmentHolders = appointmentHolders;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterAppointment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_appointment, parent, false);

        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAppointment.MyViewHolder holder, int position) {
        holder.tvAppointmentDate.setText(appointmentHolders.get(position).getAppointmentDate());
        holder.tvAppointmentTime.setText(appointmentHolders.get(position).getAppointmentTime());
        holder.tvAppointmentPatientName.setText(appointmentHolders.get(position)
                .getAppointmentPatientName());
        holder.tvAppointmentApproval.setText(appointmentHolders.get(position)
                .getAppointmentApproval());

        if (appointmentHolders.get(position).getPastOrUpcoming() ==
                RecyclerViewHolderAppointment.PAST_APPOINTMENT) {
            holder.type = RecyclerViewHolderAppointment.PAST_APPOINTMENT;
        }

        else {
            holder.type = RecyclerViewHolderAppointment.UPCOMING_APPOINTMENT;
        }
    }

    @Override
    public int getItemCount() {
        return appointmentHolders.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppointmentDate, tvAppointmentTime, tvAppointmentPatientName,
                tvAppointmentApproval;

        int type;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            tvAppointmentDate = itemView.findViewById(R.id.textAppointmentDate);
            tvAppointmentTime = itemView.findViewById(R.id.textAppointmentTime);
            tvAppointmentPatientName = itemView.findViewById(R.id.textAppointmentPatientName);
            tvAppointmentApproval = itemView.findViewById(R.id.textAppointmentApproval);

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
