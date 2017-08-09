package com.maxibi.kamusku.kamusku_beta1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 8/9/2017.
 */

public class CusAdapter extends RecyclerView.Adapter<CusAdapter.ViewHolder> {

    private ArrayList<WordObject> wObject;
    Boolean check = false;


    public CusAdapter ( ArrayList<WordObject> wObject){
        this.wObject = wObject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_kamusku,parent,false);

        final ViewHolder myViewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check) {
                    myViewHolder.bertukar.animate()
                            .alpha(0.0f).setDuration(1000);

                    myViewHolder.bertukar.setVisibility(View.GONE);
                    check = true;
                } else {
                    myViewHolder.bertukar.setVisibility(View.VISIBLE);
                    myViewHolder.bertukar.animate()
                            .alpha(1.0f).setDuration(1000);
                    check = false;

                }
            }
        });

            return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView word_MS = holder.word_MS;
        TextView word_EN = holder.word_EN;

        word_MS.setText(wObject.get(position).getWordBM());
        word_EN.setText(wObject.get(position).getWordBI());
    }

    @Override
    public int getItemCount() {
        return wObject.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView word_MS, word_EN;
        RelativeLayout bertukar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.word_MS = (TextView) itemView.findViewById(R.id.word_MS);
            this.word_EN = (TextView)itemView.findViewById(R.id.word_EN);
            this.bertukar = (RelativeLayout) itemView.findViewById(R.id.expandableLayout);

        }
    }
}
