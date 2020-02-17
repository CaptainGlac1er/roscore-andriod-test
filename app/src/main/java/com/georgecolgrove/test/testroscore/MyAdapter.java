package com.georgecolgrove.test.testroscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.ros.node.NodeMain;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    HashMap<String, NodeMain> nodes;
    NodeCommander nodeCommander;

    public MyAdapter(Context context, HashMap<String, NodeMain> nodes, NodeCommander nodeCommander) {
        this.context = context;
        this.nodes = nodes;
        this.nodeCommander = nodeCommander;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.node_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String id = (new ArrayList<>(nodes.keySet())).get(position);
        System.out.println(id);
        holder.label.setText(id);
        holder.executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nodeCommander.executeNode(nodes.get(id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return nodes.keySet().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        Button executeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.textView);
            executeButton = itemView.findViewById(R.id.executeNode);
        }
    }
}
