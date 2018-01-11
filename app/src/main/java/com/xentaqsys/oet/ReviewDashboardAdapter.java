package com.xentaqsys.oet;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 8/12/15.
 */
public class ReviewDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;





    private List<ReviewDashboardItems> data_List;


    private Context mContext;


    public ReviewDashboardAdapter(Context context, List<ReviewDashboardItems> mDataset) {

        this.data_List = mDataset;
        this.mContext = context;


    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > data_List.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
            return new GenericViewHolder(v);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {


        } else if (holder instanceof GenericViewHolder) {
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            genericViewHolder.textViewAttempt.setText(data_List.get(position-1).getAttempt());
            genericViewHolder.textViewRight.setText(data_List.get(position-1).getRight());
            genericViewHolder.textViewWrong.setText(data_List.get(position-1).getWrong());
            genericViewHolder.textViewMarks.setText(data_List.get(position-1).getMarks());
            genericViewHolder.textViewCompleted.setText(data_List.get(position-1).getCompleted());



        }

    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        //data_List.size()+
        return data_List.size() + 1;
    }

    public class GenericViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewAttempt, textViewRight, textViewWrong, textViewMarks, textViewCompleted;

        public GenericViewHolder(View view) {
            super(view);

            this.textViewAttempt = (TextView) view.findViewById(R.id.textViewAttempt);
            this.textViewRight = (TextView) view.findViewById(R.id.textviewRight);
            this.textViewWrong = (TextView) view.findViewById(R.id.textviewWrong);
            this.textViewMarks = (TextView) view.findViewById(R.id.textviewMarks);
            this.textViewCompleted = (TextView) view.findViewById(R.id.textviewCompleted);


        }


    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            //this.txtTitleHeader = (TextView) itemView.findViewById (R.id.txtHeader);
        }
    }

}
