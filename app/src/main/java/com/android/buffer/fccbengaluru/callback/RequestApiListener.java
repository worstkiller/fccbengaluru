package com.android.buffer.fccbengaluru.callback;

import com.android.buffer.fccbengaluru.model.FeedModel;

/**
 * Created by incred on 8/12/17.
 */

public interface RequestApiListener {
    public void onFeedsReceived(FeedModel feedModel);
    public void onApiFailure();
}
