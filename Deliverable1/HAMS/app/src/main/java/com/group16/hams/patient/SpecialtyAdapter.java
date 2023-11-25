package com.group16.hams.patient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group16.hams.R;
import com.group16.hams.SpecialtyClicked;

import java.util.List;

import entities.Specialty;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.SpecialtyViewHolder> {

    Context context;
    private List<Specialty> specialtyList;
    public SpecialtyAdapter(Context context, List<Specialty> specialtyList) {
        this.context = context;
        this.specialtyList = specialtyList;
    }

    public void setFilteredList(List<Specialty> filteredList) {
        this.specialtyList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpecialtyAdapter.SpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.each_specialty, parent, false);

        return new SpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyAdapter.SpecialtyViewHolder holder, int position) {
        Specialty specialty = specialtyList.get(position);
        holder.tvSpecialty.setText(specialty.getSpecialty());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                specialtyClicked(specialty.getSpecialty());
            }
        });
    }

    private void specialtyClicked(String specialty) {
        Intent intent = new Intent(context, SpecialtyClicked.class);

        intent.putExtra("Specialty Holder", specialty);
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return specialtyList.size();
    }

    public static class SpecialtyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSpecialty;
        public SpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSpecialty = itemView.findViewById(R.id.specialtyName);
        }
    }
}
