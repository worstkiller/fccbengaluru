package com.android.buffer.fccbengaluru.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.callback.OnItemClickListener;
import com.android.buffer.fccbengaluru.model.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by incred on 8/12/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<DataModel> mDataModels = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public HomeAdapter(final List<DataModel> feedModelList, OnItemClickListener clickListener) {
        mDataModels = feedModelList;
        mOnItemClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_feeds, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DataModel feedModel = mDataModels.get(position);
        if (!TextUtils.isEmpty(feedModel.getMessage())) {
            holder.tvFeedMessage.setText(feedModel.getMessage());
        } else if (!TextUtils.isEmpty(feedModel.getStory())) {
            holder.tvFeedMessage.setText(feedModel.getStory());
        } else {
            holder.mCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvFeedMessage;
        private CardView mCardView;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvFeedMessage = itemView.findViewById(R.id.tvFeedMessage);
            mCardView = itemView.findViewById(R.id.cvFeed);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            //here notify the click
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
