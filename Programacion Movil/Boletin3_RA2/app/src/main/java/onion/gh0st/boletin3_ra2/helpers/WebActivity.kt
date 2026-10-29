package onion.gh0st.boletin3_ra2.helpers

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import onion.gh0st.boletin3_ra2.R

class WebActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val URL_DEFAULT = "https://www.iesfranciscodelosrios.es"
    }

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_web)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        val url = intent.getStringExtra(EXTRA_URL) ?: URL_DEFAULT
        webView.loadUrl(url)
    }

    fun initComponents() {
        webView = findViewById<WebView>(R.id.webView)
    }
}