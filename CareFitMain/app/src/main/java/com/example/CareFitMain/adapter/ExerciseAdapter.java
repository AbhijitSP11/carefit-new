package com.example.CareFitMain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.CareFitMain.R;
import com.example.CareFitMain.model.Exercises;
import com.example.CareFitMain.model.Trainers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyHolder> {

    private Context context;
    private final ArrayList<Exercises> arrayList;
    private static ExerciseAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ExerciseAdapter.OnItemClickListener listener)
    {
        ExerciseAdapter.listener = listener;
    }
    public ExerciseAdapter(Context context, ArrayList<Exercises> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ExerciseAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exercise, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.MyHolder holder, int position) {
        Exercises mExercises = arrayList.get(position);
        Picasso.get().load(mExercises.getExImage()).centerCrop().fit().noFade().into(holder.imageView);
        holder.exerciseName.setText(mExercises.getExName());
        holder.exerciseDescription.setText(mExercises.getExDescription());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView imageView;
        private final AppCompatTextView exerciseName, exerciseDescription ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.exerciseImage);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseDescription = itemView.findViewById(R.id.exerciseDesc);
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
