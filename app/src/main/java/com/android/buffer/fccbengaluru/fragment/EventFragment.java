package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.adapter.EventsAdapter;
import com.android.buffer.fccbengaluru.callback.EventClickListener;
import com.android.buffer.fccbengaluru.model.EventEntryModel;
import com.android.buffer.fccbengaluru.util.Constants;
import com.android.buffer.fccbengaluru.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by incred on 7/12/17.
 */

public class EventFragment extends BaseFragment {

    @BindView(R.id.rvEventRecycler)
    RecyclerView mRvEventRecycler;
    Unbinder unbinder;
    private EventsAdapter mEventsAdapter;
    private List<EventEntryModel> mEntryModelList = new ArrayList<>();

    /*
        returns a event fragment instance
         */
    public static EventFragment getInstance() {
        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        fetchEventsData();
    }

    private void fetchEventsData() {
        //here fetch the data from the firebase
        showProgressDialog();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_REFERENCE_EVENT);
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //get the data and parse into some pojo
                hideProgressDialog();
                for (DataSnapshot entryModel : dataSnapshot.getChildren()) {
                    EventEntryModel eventModel = entryModel.getValue(EventEntryModel.class);
                    mEntryModelList.add(eventModel);
                }
                mEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                //handle the error
                if (isAdded()) {
                    hideProgressDialog();
                    showSnackBar(getString(R.string.error_common));
                }
            }
        });
    }

    private EventClickListener getEventClickListener() {
        //init the click listener
        EventClickListener clickListener = new EventClickListener() {
            @Override
            public void onAddToCalendarClick(final int position) {
                //handle the add to calendar
                Utils.addToCalendar(getContext(), mEntryModelList.get(position));
            }

            @Override
            public void onWebsiteClick(int position) {
                //handle the website click
                Utils.openLinkToWeb(getContext(), mEntryModelList.get(position).getWebsite());
            }
        };
        return clickListener;
    }

    private void setRecyclerView() {
        //here set the recyclerview
        mEventsAdapter = new EventsAdapter(mEntryModelList, getEventClickListener());
        mRvEventRecycler.setHasFixedSize(true);
        mRvEventRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvEventRecycler.setAdapter(mEventsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
