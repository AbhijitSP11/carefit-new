package com.example.CareFitMain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CareFitMain.R;
import com.example.CareFitMain.TrainerActivity;
import com.example.CareFitMain.model.Trainers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.MyHolder> {
    private Context context;
    private final ArrayList<Trainers> arrayList;
    private static OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        TrainerAdapter.listener = listener;
    }

    public TrainerAdapter(Context context, ArrayList<Trainers> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TrainerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainer_details, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerAdapter.MyHolder holder, int position) {
        Trainers mTrainers = arrayList.get(position);
        Picasso.get().load(mTrainers.getTImage()).centerCrop().fit().noFade().into(holder.imageView);
        holder.textViewName.setText(mTrainers.getTName());
        holder.textViewFee.setText(mTrainers.getTFee());
        holder.textViewAge.setText(mTrainers.getTAge());
        holder.textViewExperience.setText(mTrainers.getTExperience());
        holder.textViewRating.setText(mTrainers.getTRating());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static  class MyHolder extends RecyclerView.ViewHolder{
        private final AppCompatImageView imageView;
        private final AppCompatTextView textViewName, textViewAge, textViewExperience, textViewRating, textViewFee ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.trainerImage);
            textViewName = itemView.findViewById(R.id.trainerName);
            textViewAge= itemView.findViewById(R.id.trainerAge);
            textViewExperience = itemView.findViewById(R.id.trainerExperience);
            textViewRating = itemView.findViewById(R.id.trainerRating);
            textViewFee = itemView.findViewById(R.id.trainerFee);
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