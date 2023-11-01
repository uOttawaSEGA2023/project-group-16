package com.group16.hams;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterUser extends RecyclerView.Adapter<RecyclerViewAdapterUser.LayoutViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<RecyclerViewHolderUser> holders;

    public RecyclerViewAdapterUser(ArrayList<RecyclerViewHolderUser> holders,
                                   RecyclerViewInterface recyclerViewInterface) {
        this.holders = holders;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public LayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row_user, parent, false);
        return new LayoutViewHolder(userLayout, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutViewHolder holder, int position) {
        holder.tvFullName.setText
                ("Full Name: " + holders.get(position).getFullName());
        holder.tvEmail.setText
                ("Email: " + holders.get(position).getEmail());
        holder.tvAddress.setText
                ("Address: " + holders.get(position).getAddress());
        holder.tvPhoneNumber.setText
                ("Phone Number: " + holders.get(position).getPhoneNumber());

        if (holders.get(position).getType() == 0) {
            holder.tvHealthOrEmployee.setText
                    ("Health Card Number: " + holders.get(position).getHealthOrEmployee());
            holder.tvSpecialties.setText
                    ("");
        }

        else {
            holder.tvHealthOrEmployee.setText
                    ("Employee Number: " + holders.get(position).getHealthOrEmployee());
            holder.tvSpecialties.setText
                    ("Specialties: " + holders.get(position).getSpecialties());
        }
    }

    @Override
    public int getItemCount() {
        return holders.size();
    }

    public static class LayoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvAddress, tvPhoneNumber, tvHealthOrEmployee, tvSpecialties ;
        public LayoutViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvFullName = (TextView) itemView.findViewById(R.id.textName);
            tvEmail = (TextView) itemView.findViewById(R.id.textEmail);
            tvAddress = (TextView) itemView.findViewById(R.id.textAddress);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.textPhoneNumber);
            tvHealthOrEmployee = (TextView) itemView.findViewById(R.id.textEmployeeOrHealth);
            tvSpecialties = (TextView) itemView.findViewById(R.id.textSpecialties);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        CardView card = view.findViewById(R.id.recycler_row);

                        if (card.getCardBackgroundColor().equals(ColorStateList.valueOf(Color.parseColor("#187064")))){
                            card.setCardBackgroundColor(Color.parseColor("#6750A4"));
                        } else {
                            card.setCardBackgroundColor(Color.parseColor("#187064"));
                        }

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



}
