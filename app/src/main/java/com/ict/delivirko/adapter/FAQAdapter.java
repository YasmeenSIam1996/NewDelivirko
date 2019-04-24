package com.ict.delivirko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ict.delivirko.Module.restaurant.Questions;
import com.ict.delivirko.R;

import java.util.List;


public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {
    Context context;
    List<Questions> objects;

    public FAQAdapter(Context context, List<Questions> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_faq, viewGroup, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        Questions questions = objects.get(i);
        myViewHolder.tvFaqQuestion.setText(questions.getQuestion());
        myViewHolder.tvFaqAnswer.setText(questions.getAnswer());

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFaqQuestion, tvFaqAnswer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFaqQuestion = itemView.findViewById(R.id.tvFaqQuestion);
            tvFaqAnswer = itemView.findViewById(R.id.tvFaqAnswer);
        }
    }
}
