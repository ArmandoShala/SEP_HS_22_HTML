package ch.zhaw.shalaar3.sep_hs_22_html

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        val webView = findViewById(R.id.webview) as WebView;
        webView.loadUrl("file:///android_asset/index.html");

    }
}