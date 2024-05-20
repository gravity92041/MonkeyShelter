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
import com.example.androidspringtestapp.model.Person;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private Context context;
    private List<Person> people;

    public PeopleAdapter(Context context, List<Person> people) {
        this.context = context;
        this.people = people;
    }
    @NonNull
    @Override
    public PeopleAdapter.PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_person,parent,false);
        return new PeopleAdapter.PeopleViewHolder(view);
    }
    public void setFilteredList(List<Person> filteredList){
        this.people=filteredList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.PeopleViewHolder holder, int position){
        Person person = people.get(position);
        holder.userNameTextView.setText(person.getUsername());
        holder.roleTextView.setText(person.getRole());
        Picasso.get().load("https://i.pinimg.com/564x/04/5c/64/045c64acd15b7178f670758807a62753.jpg").into(holder.userImageView);
    }
    @Override
    public int getItemCount(){
        return people.size();
    }
    public static class PeopleViewHolder extends RecyclerView.ViewHolder{
        public TextView userNameTextView;
        public TextView roleTextView;
        public ImageView userImageView;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            roleTextView=itemView.findViewById(R.id.roleTextView);
            userImageView=itemView.findViewById(R.id.userImageView);
        }
    }
}
