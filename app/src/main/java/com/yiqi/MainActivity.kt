package com.yiqi

import android.content.pm.ActivityInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*

class MainActivity : AppCompatActivity() {
    private var webView: WebView? = null
    private val url="http://www.wangmutan.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)
        initWebview()
    }

    private fun initWebview() {
        webView!!.loadUrl(url)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
        //启用支持Javascript
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        settings.displayZoomControls = false //隐藏webview缩放按钮
        //设置加载进来的页面自适应手机屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.allowFileAccess=true
        settings.cacheMode=WebSettings.LOAD_NO_CACHE
        //设置webview的插件转状态
        settings.pluginState=WebSettings.PluginState.ON
//        syncCookie()
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView!!,true)
        }
    }


    override fun onResume() {
        super.onResume()
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView!!.canGoBack()){
                webView!!.goBack()
                return true
            }else{
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
