package com.example.androidspringtestapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidspringtestapp.R;
import com.example.androidspringtestapp.model.Monkey;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NonNls;

import java.util.List;

public class MonkeyAdapter extends RecyclerView.Adapter<MonkeyAdapter.MonkeyViewHolder> {
    private Context context;
    private List<Monkey> monkeys;

    public MonkeyAdapter(Context context, List<Monkey> monkeys) {
        this.context = context;
        this.monkeys = monkeys;
    }

    public void setFilteredList(List<Monkey> filteredList){
        this.monkeys=filteredList;
        notifyDataSetChanged();
    }
//    public interface OnMonkeyClickListener{
//        void onMonkeyClick(String monkeyId);
//    }
//    private OnMonkeyClickListener listener;
//    public void setOnMonkeyClickListener(OnMonkeyClickListener listener){
//        this.listener=listener;
//    }

    @NonNull
    @Override
    public MonkeyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_monkey,parent,false);
        return new MonkeyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MonkeyViewHolder holder,int position){
        Monkey monkey = monkeys.get(position);
        holder.nameTextView.setText(monkey.getName());
        holder.houseTextView.setText("Обезьянке "+monkey.getAge()+" годиков");
        Picasso.get().load(monkey.getImage()).into(holder.imageView);
    }
    @Override
    public int getItemCount(){
        return monkeys.size();
    }
    public static class MonkeyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView houseTextView;
        public ImageView imageView;

        public MonkeyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            houseTextView=itemView.findViewById(R.id.houseTextView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
