package com.example.CareFitMain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CareFitMain.R;
import com.example.CareFitMain.model.Exercises;
import com.example.CareFitMain.model.FoodPlan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodPlanAdapter extends  RecyclerView.Adapter<FoodPlanAdapter.MyHolder> {
    private Context context;
    private final ArrayList<FoodPlan> arrayList;
    private static FoodPlanAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(FoodPlanAdapter.OnItemClickListener listener)
    {
        FoodPlanAdapter.listener = listener;
    }
    public FoodPlanAdapter(Context context, ArrayList<FoodPlan> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FoodPlanAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_card_view, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPlanAdapter.MyHolder holder, int position) {
        FoodPlan mFoodPlan = arrayList.get(position);
        holder.morning.setText(mFoodPlan.getMorning());
        holder.breakfast.setText(mFoodPlan.getBreakfast());
        holder.midday.setText(mFoodPlan.getMidday());
        holder.lunch.setText(mFoodPlan.getLunch());
        holder.snacks.setText(mFoodPlan.getSnacks());
        holder.dinner.setText(mFoodPlan.getDinner());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView morning,  breakfast, midday, lunch, snacks, dinner;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            morning = itemView.findViewById(R.id.morningFood);
            breakfast = itemView.findViewById(R.id.breakfast);
            midday = itemView.findViewById(R.id.midDay);
            lunch = itemView.findViewById(R.id.lunch);
            snacks = itemView.findViewById(R.id.snacks);
            dinner = itemView.findViewById(R.id.dinner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}