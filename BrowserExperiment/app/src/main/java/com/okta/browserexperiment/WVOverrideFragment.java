package com.okta.browserexperiment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * Fragment for the WebView Override
 */
public class WVOverrideFragment extends Fragment {

    public WVOverrideFragment() {
        // Required empty public constructor
    }

    OverridingWebView webview;
    OverridingWebViewClient overridingWebViewClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridingWebViewClient = new OverridingWebViewClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_wvoverride, container, false);

        webview = (OverridingWebView) layout.findViewById(R.id.wvoverride_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(overridingWebViewClient);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        webview.clearCache(true);
        webview.clearHistory();

        webview.loadUrl(getString(R.string.initial_site_url));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
