package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by incred on 7/12/17.
 */

public class AboutFragment extends BaseFragment {

    @BindView(R.id.wvAbout)
    WebView mWvAbout;
    Unbinder unbinder;

    /*
        returns a event fragment instance
         */
    public static AboutFragment getInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWvAbout.loadUrl(Constants.FEED_URL);
        WebSettings webSettings = mWvAbout.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWvAbout.setWebViewClient(new WebViewClient());
        mWvAbout.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWvAbout.canGoBack()) {
                    mWvAbout.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
