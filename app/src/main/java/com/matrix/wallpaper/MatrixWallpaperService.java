package com.matrix.wallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MatrixWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MatrixEngine();
    }

    class MatrixEngine extends Engine {

        private WebView mWebView;
        private View    mRootView;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // Inflate layout avec WebView
            LayoutInflater inflater = LayoutInflater.from(MatrixWallpaperService.this);
            mRootView = inflater.inflate(R.layout.wallpaper_layout, null);
            mWebView  = mRootView.findViewById(R.id.webview);

            // Config WebView
            WebSettings ws = mWebView.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setDomStorageEnabled(true);
            ws.setLoadWithOverviewMode(true);
            ws.setUseWideViewPort(true);
            ws.setBuiltInZoomControls(false);
            ws.setDisplayZoomControls(false);

            mWebView.setBackgroundColor(0xFF000000);
            mWebView.setWebViewClient(new WebViewClient());

            // Charger le HTML depuis les assets
            mWebView.loadUrl("file:///android_asset/matrix.html");
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            // Ajuster la taille de la WebView à l'écran
            if (mRootView != null) {
                int wSpec = View.MeasureSpec.makeMeasureSpec(width,  View.MeasureSpec.EXACTLY);
                int hSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                mRootView.measure(wSpec, hSpec);
                mRootView.layout(0, 0, width, height);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (mWebView == null) return;
            if (visible) {
                mWebView.onResume();
                mWebView.evaluateJavascript("if(typeof resumeMatrix==='function') resumeMatrix();", null);
            } else {
                mWebView.onPause();
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mWebView != null) {
                mWebView.destroy();
                mWebView = null;
            }
        }

        // Rendu sur la Surface
        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            super.onSurfaceRedrawNeeded(holder);
            drawFrame(holder);
        }

        private void drawFrame(SurfaceHolder holder) {
            if (mRootView == null) return;
            try {
                android.graphics.Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    mRootView.draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            } catch (Exception e) {
                // Ignorer les erreurs de surface
            }
        }
    }
}
