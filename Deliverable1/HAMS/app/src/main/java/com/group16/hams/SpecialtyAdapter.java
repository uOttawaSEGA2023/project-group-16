package com.group16.hams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.SpecialtyViewHolder> {

    private List<Specialty> specialtyList;
    public SpecialtyAdapter(List<Specialty> specialtyList) {
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
