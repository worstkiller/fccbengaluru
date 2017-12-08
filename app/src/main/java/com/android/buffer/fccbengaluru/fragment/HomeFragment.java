package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.adapter.HomeAdapter;
import com.android.buffer.fccbengaluru.callback.OnItemClickListener;
import com.android.buffer.fccbengaluru.callback.RequestApiListener;
import com.android.buffer.fccbengaluru.model.DataModel;
import com.android.buffer.fccbengaluru.model.FeedModel;
import com.android.buffer.fccbengaluru.repository.RequestProvider;
import com.android.buffer.fccbengaluru.util.Constants;
import com.android.buffer.fccbengaluru.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by incred on 7/12/17.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rvFeeds)
    RecyclerView mRvFeeds;

    Unbinder unbinder;
    private HomeAdapter mHomeAdapter;
    private List<DataModel> mDataModelList = new ArrayList<>();

    /*
        returns a ${HomeFragment}
         */
    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFeedRecyclerView();
        getFeedsData();
    }

    private void getFeedsData() {
        //request the data from the server
        showProgressDialog();
        RequestProvider requestProvider = new RequestProvider(listenFeedData());
        requestProvider.getFeeds(Constants.GROUP_ID, Constants.TOKEN);
    }

    private RequestApiListener listenFeedData() {
        return new RequestApiListener() {
            @Override
            public void onFeedsReceived(final FeedModel feedModel) {
                hideProgressDialog();
                mDataModelList.clear();
                mDataModelList.addAll(feedModel.getModelList());
                mHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onApiFailure() {
                hideProgressDialog();
                showSnackBar(getString(R.string.error_common));
            }
        };
    }

    private void setFeedRecyclerView() {
        //set the recyclerview for the facebook feeds
        mHomeAdapter = new HomeAdapter(mDataModelList, getItemClickListener());
        mRvFeeds.setHasFixedSize(true);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvFeeds.setAdapter(mHomeAdapter);
    }

    private OnItemClickListener getItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                //handle the item click
                Utils.openLinkToWeb(getContext(), Constants.FEED_URL);
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
