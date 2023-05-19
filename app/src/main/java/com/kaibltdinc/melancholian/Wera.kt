package com.kaibltdinc.melancholian

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kaibltdinc.melancholian.databinding.ActivityWeraBinding
import javax.inject.Inject
import org.koin.android.ext.android.get

class Wera : AppCompatActivity() {
    @Inject
    lateinit var saving: Saving
    companion object {
        private var loadm: ValueCallback<Array<Uri>>? = null
        private const val REQ = 123
    }
    private lateinit var binding: ActivityWeraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seti()
        coo()
        fil()
        val saving: Saving = get()
            if(!saving.checkLocas) {
            binding.wera.loadUrl(saving.finale_l)
        } else {
                val intent = Intent(this@Wera, Game::class.java)
                startActivity(intent)
                finish()
            }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ) {
            if (loadm == null) return
            loadm!!.onReceiveValue(
                WebChromeClient.FileChooserParams.parseResult(
                    resultCode,
                    data
                )
            )
            loadm = null
        }
    }
    private fun fil() {
        val activity: Wera = this
        binding.wera.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                web: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?,
            ): Boolean {
                if (ContextCompat.checkSelfPermission(
                        this@Wera,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@Wera,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQ
                    )
                }
                if (loadm != null) {
                    loadm!!.onReceiveValue(null)
                    loadm = null
                }
                loadm = filePathCallback
                val intent = fileChooserParams?.createIntent()
                try {
                    if (intent != null) {
                        startActivityForResult(intent, REQ)
                    }
                } catch (e: ActivityNotFoundException) {
                    loadm = null
                    Toast.makeText(activity, "Cannot open file chooser", Toast.LENGTH_LONG).show()
                    return false
                }
                return true
            }

        }

    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.wera.canGoBack()) {
            binding.wera.goBack()
        } else {
            super.onBackPressed()
        }
    }
    private fun coo() {
        CookieManager.getInstance().flush()
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.wera, true)
    }

    private fun seti() {
        binding.wera.settings.domStorageEnabled = true
        binding.wera.settings.useWideViewPort = true
        binding.wera.settings.setSupportMultipleWindows(true)
        binding.wera.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.wera.settings.setEnableSmoothTransition(true)
        binding.wera.settings.javaScriptEnabled = true
        binding.wera.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.wera.settings.allowContentAccess = true
        binding.wera.settings.apply {
            userAgentString = userAgentString.replaceAfter(")", "")
        }
        binding.wera.settings.saveFormData = true
        binding.wera.settings.loadWithOverviewMode = true
        binding.wera.settings.mixedContentMode = 0
        binding.wera.settings.savePassword = true
        binding.wera.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        binding.wera.settings.setSupportZoom(false)
        binding.wera.settings.allowUniversalAccessFromFileURLs = true
        binding.wera.settings.databaseEnabled = true
        binding.wera.settings.allowFileAccess = true
        binding.wera.settings.loadsImagesAutomatically = true
        binding.wera.settings.allowFileAccessFromFileURLs = true

        binding.wera.webViewClient = MyWebViewClient(this)

    }
    private class MyWebViewClient(mainActivity: Wera) : WebViewClient() {
        private val handler = Handler()
        private val prefs: Saving by lazy { Saving(mainActivity) }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                view.context?.startActivity(intent)
                return true
            }
            if (url.startsWith("https://diia.app")) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                        addCategory(Intent.CATEGORY_BROWSABLE)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        setPackage("ua.gov.diia.app")
                    }
                    view.context?.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        view.context,
                        "You don't have the Dia app",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return true
            }
            if (url.startsWith("mailto:")) {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                intent.putExtra(Intent.EXTRA_TEXT, "Email body")
                view.context?.startActivity(intent)
                return true
            }
            if (request.url.toString().contains("localhost")) {
                prefs.checkLocas = true
                val intent = Intent(view.context, Game::class.java)
                view.context.startActivity(intent)
                return true
            }
            if (url.startsWith("privat24")) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                        addCategory(Intent.CATEGORY_BROWSABLE)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        setPackage("ua.privatbank.ap")
                    }
                    view.context?.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        view.context,
                        "You don't have the Privat24",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return true
            }
            view.loadUrl(url)
            prefs.lk = url
            handler.postDelayed({
                if (prefs.lk == url) {
                    prefs.finale_l = url
                }
            }, 1250)
            return true
        }
    }
}