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
import com.example.CareFitMain.model.Therapists;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private final ArrayList<Therapists> arrayList;
    private static OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        MyAdapter.listener = listener;
    }

    public MyAdapter(Context context, ArrayList<Therapists> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.therapist_deatils_activity, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
    Therapists mTherapists = arrayList.get(position);
    Picasso.get().load(mTherapists.getImage()).centerCrop().fit().noFade().into(holder.imageView);
    holder.textViewName.setText(mTherapists.getName());
    holder.textViewAge.setText(mTherapists.getTage());
        holder.textViewAddress.setText(mTherapists.getTaddress());
        holder.textViewBio.setText(mTherapists.getTbio());
        holder.textViewRating.setText(mTherapists.getTratings());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static  class MyHolder extends RecyclerView.ViewHolder{
        private final AppCompatImageView imageView;
        private final AppCompatTextView textViewName, textViewAge, textViewAddress, textViewRating, textViewBio;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.therapistImage);
            textViewName = itemView.findViewById(R.id.therapistName);
            textViewAge = itemView.findViewById(R.id.therapistAge);
            textViewAddress = itemView.findViewById(R.id.therapistAdd);
            textViewBio = itemView.findViewById(R.id.therapistBio);
            textViewRating = itemView.findViewById(R.id.therapistFee);
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
