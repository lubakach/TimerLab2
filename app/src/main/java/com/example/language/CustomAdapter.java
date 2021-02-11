package com.example.language;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    int position;
    Activity activity;
    private ArrayList timer_id, timer_name, timer_prep, timer_work, timer_rest, timer_cycle, timer_calm, timer_color;


    CustomAdapter(Activity activity, Context context,
                  ArrayList timer_id,
                  ArrayList timer_name,
                  ArrayList timer_prep,
                  ArrayList timer_work,
                  ArrayList timer_rest,
                  ArrayList timer_cycle,
                  ArrayList timer_calm,
                  ArrayList timer_color){
        this.activity = activity;
        this.context = context;
        this.timer_id = timer_id;
        this.timer_name = timer_name;
        this.timer_prep = timer_prep;
        this.timer_work = timer_work;
        this.timer_rest = timer_rest;
        this.timer_cycle = timer_cycle;
        this.timer_calm = timer_calm;
        this.timer_color = timer_color;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        this.position =position;
        holder.timer_id_text.setText(String.valueOf(timer_id.get(position)));
        holder.timer_name_txt.setText(String.valueOf(timer_name.get(position)));
        holder.timer_pre_txt.setText(String.valueOf(timer_prep.get(position)));
        holder.timer_work_txt.setText(String.valueOf(timer_work.get(position)));
        holder.timer_rest_txt.setText(String.valueOf(timer_rest.get(position)));
        holder.timer_cycle_txt.setText(String.valueOf(timer_cycle.get(position)));
        holder.timer_calm_txt.setText(String.valueOf(timer_calm.get(position)));
        holder.timer_color_txt.setText(String.valueOf(timer_color.get(position)));
        int color = Integer.parseInt(String.valueOf(timer_color.get(position)));
        holder.timer_name_txt.setTextColor(color);

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActionActivity.class);
                intent.putExtra("id", String.valueOf(timer_id.get(position)));
                intent.putExtra("Name", String.valueOf(timer_name.get(position)));
                intent.putExtra("Preparation", String.valueOf(timer_prep.get(position)));
                intent.putExtra("Work", String.valueOf(timer_work.get(position)));
                intent.putExtra("Rest", String.valueOf(timer_rest.get(position)));
                intent.putExtra("Cycle", String.valueOf(timer_cycle.get(position)));
                intent.putExtra("Calm", String.valueOf(timer_calm.get(position)));
                intent.putExtra("Color", String.valueOf(timer_color.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(timer_id.get(position)));
                intent.putExtra("Name", String.valueOf(timer_name.get(position)));
                intent.putExtra("Preparation", String.valueOf(timer_prep.get(position)));
                intent.putExtra("Work", String.valueOf(timer_work.get(position)));
                intent.putExtra("Rest", String.valueOf(timer_rest.get(position)));
                intent.putExtra("Cycle", String.valueOf(timer_cycle.get(position)));
                intent.putExtra("Calm", String.valueOf(timer_calm.get(position)));
                intent.putExtra("Color", String.valueOf(timer_color.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return timer_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout startLayout;
        Button update, start;
        TextView timer_id_text, timer_name_txt, timer_pre_txt, timer_work_txt, timer_rest_txt, timer_cycle_txt, timer_calm_txt, timer_color_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timer_id_text = itemView.findViewById(R.id.timer_id_text);
            timer_name_txt = itemView.findViewById(R.id.timer_name_txt);
            timer_pre_txt = itemView.findViewById(R.id.timer_pre_txt);
            timer_work_txt = itemView.findViewById(R.id.timer_work_text);
            timer_rest_txt = itemView.findViewById(R.id.timer_rest_txt);
            timer_cycle_txt = itemView.findViewById(R.id.timer_cycle_txt);
            timer_calm_txt = itemView.findViewById(R.id.timer_calm_txt);
            timer_color_txt = itemView.findViewById(R.id.timer_color_txt);
            startLayout = itemView.findViewById(R.id.startLayout);
            update = itemView.findViewById(R.id.update_pass);
            start = itemView.findViewById(R.id.button_start);
        }
    }
}
