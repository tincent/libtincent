/**
 * 
 */
package com.tincent.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.util.TXNetworkUtil;
import com.tincent.demo.Constants;
import com.tincent.demo.R;
import com.tincent.demo.util.Debug;
import com.tincent.demo.util.PopwdUtil;

/**
 * 浏览器界面
 * 
 * @author hui.wang
 * @date 2015.3.26
 * 
 */
public class WebviewActivity extends BaseActivity implements DownloadListener {
	private IWXAPI wxApi;
	private TextView tvTitle;
	private WebView webView;
	private View emptyView;
	// 分享popwd
	private PopupWindow sheraPopwd;

	private String title;
	private String webUrl;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			// finish();
			onBackPressed();
			break;
		case R.id.more:
			showSharePopwd(v);
			// finish();
			break;
		case R.id.stub_tip:
			webView.loadUrl(webUrl);
			emptyView.setVisibility(View.GONE);
			break;

		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case Constants.MSG_NETWORK_TIMEOUT:
			break;
		}
		return false;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_webview);

	}

	@Override
	public void initData() {
		wxApi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
		wxApi.registerApp(Constants.WEIXIN_APP_ID);

		title = getIntent().getStringExtra(Constants.KEY_WEBVIEW_TITLE);
		webUrl = getIntent().getStringExtra(Constants.KEY_WEB_URL);

		tvTitle.setText(title);

		webView.loadUrl(webUrl);
	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		ImageButton menu = (ImageButton) findViewById(R.id.more);
		menu.setVisibility(View.VISIBLE);
		menu.setImageResource(R.drawable.icon_setting);
		menu.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.title);

		emptyView = (TextView) findViewById(R.id.stub_tip);
		emptyView.setOnClickListener(this);

		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setGeolocationEnabled(true);
		String dir = getApplicationContext().getDir("databases", Context.MODE_PRIVATE).getPath();
		webView.getSettings().setGeolocationDatabasePath(dir);
		webView.setDownloadListener(this);

		if (TXNetworkUtil.isNetworkConnected(this)) {
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		} else {
			webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}

		webView.setWebChromeClient(new WebChromeClient() {
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Debug.o(new Exception(), "url = " + url);
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Debug.o(new Exception(), "url = " + url);
				// showLoading();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Debug.o(new Exception(), "url = " + url);
				mMainHandler.removeMessages(Constants.MSG_NETWORK_TIMEOUT);
				// hideLoading();
			}

			@Override
			public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
				Debug.o(new Exception(), "event = " + event);
				super.onUnhandledKeyEvent(view, event);
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				Debug.o(new Exception(), "event = " + event);
				return super.shouldOverrideKeyEvent(view, event);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// super.onReceivedError(view, errorCode, description,
				// failingUrl);
				view.loadData("", "text/html; charset=UTF-8", null);
				emptyView.setVisibility(View.VISIBLE);
			}

		});

	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	private void showSharePopwd(View parent) {
		sheraPopwd = PopwdUtil.showShareByWeixin(this, parent, new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.sendfrient:
					// ToastUtil.showMessage("发送给朋友");
					shareByWeixin(0);
					sheraPopwd.dismiss();
					break;
				case R.id.sharefriend:
					// ToastUtil.showMessage("分享到朋友圈");
					shareByWeixin(1);
					sheraPopwd.dismiss();
					break;

				}

			}
		});

	}

	/**
	 * 微信分享
	 * 
	 * @param flag
	 *            0：微信好友;1:微信朋友圈
	 */
	private void shareByWeixin(int flag) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = webUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = "该内容由亚心健康提供，欢迎访问";
		// 这里替换一张自己工程里的图片资源
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		msg.setThumbImage(thumb);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);

	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		// TODO Auto-generated method stub

	}

}
