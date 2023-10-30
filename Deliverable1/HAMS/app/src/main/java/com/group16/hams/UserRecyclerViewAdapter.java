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

import static com.group16.hams.UserRecyclerViewAdapter.PatientLayoutViewHolder;

import entities.Patient;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter {
    ArrayList<RecyclerViewHolder> holders;
    public UserRecyclerViewAdapter(ArrayList<RecyclerViewHolder> holders) {
        this.holders = holders;
    }

    public int getItemViewType(int position) {
        switch (holders.get(position).getLayoutType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return -1;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder result = null;
        switch (viewType) {
            case 0:
                System.out.println("Case 0 works");
                View patientLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_row_patient, parent, false);
                result = new PatientLayoutViewHolder(patientLayout);
                break;
            case 1:
                System.out.println("Case 1 works");
                View doctorLayout = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_row_doctor, parent, false);
                result = new DoctorLayoutViewHolder(doctorLayout);
                break;
            default:
                System.out.println("Case default works");
                break;
        }
        return result;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                System.out.println("Case 0 works");
                ((PatientLayoutViewHolder)holder).tvFullName.setText
                        ("Full Name: " + holders.get(position).getFullName());
                ((PatientLayoutViewHolder)holder).tvEmail.setText
                        ("Email: " + holders.get(position).getEmail());
                ((PatientLayoutViewHolder)holder).tvAddress.setText
                        ("Address: " + holders.get(position).getAddress());
                ((PatientLayoutViewHolder)holder).tvPhoneNumber.setText
                        ("Phone Number: " + holders.get(position).getPhoneNumber());
                ((PatientLayoutViewHolder)holder).tvHealthCardNumber.setText
                        ("Health Card Number: " + holders.get(position).getHealthCardNumber());
                break;
            case 1:
                System.out.println("Case 1 works");
                ((DoctorLayoutViewHolder)holder).tvFullName.setText
                        ("Full Name: " + holders.get(position).getFullName());
                ((DoctorLayoutViewHolder)holder).tvEmail.setText
                        ("Email: " + holders.get(position).getEmail());
                ((DoctorLayoutViewHolder)holder).tvAddress.setText
                        ("Address: " + holders.get(position).getAddress());
                ((DoctorLayoutViewHolder)holder).tvPhoneNumber.setText
                        ("Phone Number: " + holders.get(position).getPhoneNumber());
                ((DoctorLayoutViewHolder)holder).tvEmployeeNumber.setText
                        ("Employee Number: " + holders.get(position).getEmployeeNumber());
                ((DoctorLayoutViewHolder)holder).tvSpecialties.setText
                        ("Specialties: " + holders.get(position).getSpecialties());
                break;
            default:
                System.out.println("Case default works");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return holders.size();
    }

    public static class PatientLayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvAddress, tvPhoneNumber, tvHealthCardNumber;
        public PatientLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.textName);
            tvEmail = itemView.findViewById(R.id.textEmail);
            tvAddress = itemView.findViewById(R.id.textAddress);
            tvPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);
            tvHealthCardNumber = itemView.findViewById(R.id.textHealthCardNumber);
        }
    }

    public static class DoctorLayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvAddress, tvPhoneNumber, tvEmployeeNumber, tvSpecialties;

        public DoctorLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.textName);
            tvEmail = itemView.findViewById(R.id.textEmail);
            tvAddress = itemView.findViewById(R.id.textAddress);
            tvPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);
            tvEmployeeNumber = itemView.findViewById(R.id.textEmployeeNumber);
            tvSpecialties = itemView.findViewById(R.id.textSpecialties);
        }
    }

}
