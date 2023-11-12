package com.example.tilt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> datalist;

    public MyAdapter(Context context, List<DataClass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(datalist.get(position).getDateImage()).into(holder.recImage);
        holder.recTitle.setText(datalist.get(position).getDateTitle());
        holder.recDesc.setText(datalist.get(position).getDateDesc());
        holder.recLang.setText(datalist.get(position).getDateLang());

        holder.recCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("Image", datalist.get(holder.getAdapterPosition()).getDateImage());
            intent.putExtra("Description", datalist.get(holder.getAdapterPosition()).getDateDesc());
            intent.putExtra("Title", datalist.get(holder.getAdapterPosition()).getDateTitle());
            intent.putExtra("Key", datalist.get(holder.getAdapterPosition()).getKey());
            intent.putExtra("Language", datalist.get(holder.getAdapterPosition()).getDateLang());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public void searchDataList(ArrayList<DataClass> searchList){
        datalist = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle,recDesc,recLang;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recLang = itemView.findViewById(R.id.recLang);
        recTitle = itemView.findViewById(R.id.recTitle);

    }
}