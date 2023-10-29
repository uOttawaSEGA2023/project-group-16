package com.group16.hams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.function.DoubleConsumer;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<RecyclerViewHolder> holders;
    public UserRecyclerViewAdapter(Context context, ArrayList<RecyclerViewHolder> holders) {
        this.context = context;
        this.holders = holders;
    }

    @NonNull
    @Override
    public UserRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        //NEED TO FIX THIS
        View view = inflater.inflate(R.layout.recycler_view_row_doctor, parent, false);

        return new UserRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvFullName.setText("Full Name: " + holders.get(position).getFullName());
        holder.tvEmail.setText("Email: " + holders.get(position).getEmail());
        holder.tvAddress.setText("Address: " + holders.get(position).getAddress());
        holder.tvPhoneNumber.setText("Phone Number: " + holders.get(position).getPhoneNumber());

        if (holder.type.equals("Patient")) {
            holder.tvHealthCardNumber.setText("Health Card Number: " +
                    ((PatientRecyclerViewHolder) holders.get(position)).getHealthCardNumber());
        }

        else {
            holder.tvEmployeeNumber.setText("Employee Number: " +
                    ((DoctorRecyclerViewHolder) holders.get(position)).getEmployeeNumber());
            holder.tvSpecialties.setText("Employee Number: " +
                    ((DoctorRecyclerViewHolder) holders.get(position)).getSpecialties());
        }
    }

    @Override
    public int getItemCount() {
        return holders.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvAddress, tvPhoneNumber, tvHealthCardNumber,
                tvEmployeeNumber, tvSpecialties;
        String type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.textName);
            tvEmail = itemView.findViewById(R.id.textEmail);
            tvAddress = itemView.findViewById(R.id.textAddress);
            tvPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);

            //Not sure how else to deal with finding whether the user is a doctor or patient
            try {
                tvHealthCardNumber = itemView.findViewById(R.id.textHealthCardNumber);
                type = "Patient";
            }
            catch (Exception e) {}

            try {
                tvEmployeeNumber = itemView.findViewById(R.id.textEmployeeNumber);
                tvSpecialties = itemView.findViewById(R.id.textSpecialties);
                type = "Doctor";
            }
            catch (Exception e) {}
        }
    }
}
