package com.teazytech.hindishayari;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    // 1- data
    private Context context;
    private List<Item> items;

    // 2- Create constructor
    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.postTitle.setText(item.getTitle());

        Document document = Jsoup.parse(item.getContent());
        holder.postDiscroption.setText(document.text());
        Elements elements = document.select("img");
        Log.d("CODE", "Image - "+ elements.get(0).attr("src"));
        Log.d("TEXT", document.text());

        Glide.with(context).load(elements.get(0).attr("src")).into(holder.postImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("url", item.getUrl());
                intent.putExtra("img", elements.get(0).attr("src"));
                intent.putExtra("title", item.getTitle());
                intent.putExtra("discription", item.getContent());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 3 - create View Holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView postImage;
        private TextView postTitle;
        private TextView postDiscroption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage =  itemView.findViewById(R.id.postImage);
            postTitle =  itemView.findViewById(R.id.postTitle);
            postDiscroption =  itemView.findViewById(R.id.postDiscription);
        }
    }
}
