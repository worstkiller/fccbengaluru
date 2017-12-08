package com.android.buffer.fccbengaluru.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.callback.EventClickListener;
import com.android.buffer.fccbengaluru.model.EventEntryModel;
import com.android.buffer.fccbengaluru.util.Utils;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by incred on 8/12/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<EventEntryModel> mEntryModelList = new ArrayList<>();
    private EventClickListener mEventClickListener;
    private Context mContext;

    public EventsAdapter(final List<EventEntryModel> entryModelList, EventClickListener eventClickListener) {
        mEntryModelList = entryModelList;
        mEventClickListener = eventClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        EventEntryModel eventEntryModel = mEntryModelList.get(position);
        if (!TextUtils.isEmpty(eventEntryModel.getTitle())) {
            holder.title.setText(eventEntryModel.getTitle());
        }
        if (!TextUtils.isEmpty(eventEntryModel.getDate())) {
            try {
                String date = Utils.getParsedDateFormat(eventEntryModel.getDate());
                holder.date.setText(date);
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
                holder.date.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(eventEntryModel.getImageUrl())) {
            Glide.with(mContext).load(eventEntryModel.getImageUrl()).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mEntryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date, title;
        private ImageView mImageView;
        private AppCompatButton website, addToCalendar;

        public ViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvRowMainDate);
            mImageView = itemView.findViewById(R.id.ivRowMainCover);
            title = itemView.findViewById(R.id.tvRowMainTitle);
            website = itemView.findViewById(R.id.btWebsite);
            addToCalendar = itemView.findViewById(R.id.btAddCalendar);
            website.setOnClickListener(this);
            addToCalendar.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            //here notify the click
            if (view.getId() == R.id.btAddCalendar) {
                mEventClickListener.onAddToCalendarClick(getAdapterPosition());
            } else {
                mEventClickListener.onWebsiteClick(getAdapterPosition());
            }

        }
    }
}
