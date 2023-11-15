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

public class RecyclerViewAdapterShift extends RecyclerView.Adapter<RecyclerViewAdapterShift.MyViewHolder>{


    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<RecyclerViewHolderShift> shiftHolders;

    public RecyclerViewAdapterShift(Context context, ArrayList<RecyclerViewHolderShift> shiftHolders, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.shiftHolders = shiftHolders;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterShift.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_shift, parent, false);
        return new RecyclerViewAdapterShift.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterShift.MyViewHolder holder, int position) {

        holder.tvShiftDate.setText(shiftHolders.get(position).getShiftDate());
        holder.tvShiftStartTime.setText(shiftHolders.get(position).getShiftStartTime());
        holder.tvShiftEndTime.setText(shiftHolders.get(position).getShiftEndTime());

        if (shiftHolders.get(position).getPastOrUpcoming() == RecyclerViewHolderAppointment.PAST_APPOINTMENT) {
            holder.type = RecyclerViewHolderAppointment.PAST_APPOINTMENT;
        }

        else {
            holder.type = RecyclerViewHolderAppointment.UPCOMING_APPOINTMENT;
        }

    }

    @Override
    public int getItemCount() {
        return shiftHolders.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvShiftDate, tvShiftStartTime, tvShiftEndTime;

        int type;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            tvShiftDate = itemView.findViewById(R.id.textShiftDate);
            tvShiftStartTime = itemView.findViewById(R.id.textShiftStartTime);
            tvShiftEndTime = itemView.findViewById(R.id.textShiftEndTime);

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
