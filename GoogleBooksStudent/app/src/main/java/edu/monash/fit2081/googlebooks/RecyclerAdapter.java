package edu.monash.fit2081.googlebooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<GoogleBook> books;

    public RecyclerAdapter (ArrayList<GoogleBook> data) {
        super();
        books = data;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(books.get(position).getBookTitle());
        holder.itemAuthor.setText(books.get(position).getAuthors());
        holder.itemDate.setText(books.get(position).getPublishedDate().substring(0, 4));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemTitle;
        public TextView itemAuthor;
        public TextView itemDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemTitle = itemView.findViewById(R.id.title);
            itemAuthor = itemView.findViewById(R.id.author);
            itemDate = itemView.findViewById(R.id.date);
        }
    }
}
