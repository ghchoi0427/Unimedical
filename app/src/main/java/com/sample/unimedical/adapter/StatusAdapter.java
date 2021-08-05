package com.sample.unimedical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.status.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    public ArrayList<Status> statuses = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View statusView = layoutInflater.inflate(R.layout.status_item, parent, false);

        return new ViewHolder(statusView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        Status status = statuses.get(position);
        holder.setItem(status);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void addItem(Status status) {
        statuses.add(status);
    }

    public void setItems(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    public void clearItem() {
        statuses.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStatusPrefix;
        TextView textStatusContent;

        public ViewHolder(View statusView) {
            super(statusView);

            textStatusPrefix = statusView.findViewById(R.id.text_status_prefix);
            textStatusContent = statusView.findViewById(R.id.text_status_content);

        }

        public void setItem(Status status) {
            try {

                StringBuilder prefixBuilder = new StringBuilder();
                StringBuilder contentBuilder = new StringBuilder();

                List<String> list = status.getAllAttributes();
                for (String s : list) {

                    prefixBuilder.append(s.split("/")[0]).append("\n");
                    contentBuilder.append(s.split("/")[1]).append("\n");
                }

                textStatusPrefix.setText(prefixBuilder.toString());
                textStatusContent.setText(contentBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
