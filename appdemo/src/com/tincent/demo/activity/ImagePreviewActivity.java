/**
 * 
 */
package com.tincent.demo.activity;

import java.io.File;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.util.TXDialogUtil;
import com.tincent.android.util.TXFileUtils;
import com.tincent.demo.Constants;
import com.tincent.demo.R;
import com.tincent.demo.adapter.ImagePagerAdapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图片预览器
 * 
 * @author hui.wang
 * @date 2015.5.14
 * 
 */
public class ImagePreviewActivity extends BaseActivity implements OnPageChangeListener, ImageLoadingListener {

	private ViewPager viewPager;
	private ImagePagerAdapter adapter;
	private Dialog saveDialog;

	private ImageLoader imageLoader;

	private int position;// 当前显示的第几页图片
	private List<String> uriList;// 图片集
	private TextView tvTitle;
	private int type = 1;// 0本地，1网路

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_image_preview);
	}

	@Override
	public void initData() {
		imageLoader = ImageLoader.getInstance();

		if (getIntent().hasExtra("position")) {
			position = getIntent().getIntExtra("position", 0);
		}
		if (getIntent().hasExtra("uriList")) {
			uriList = getIntent().getStringArrayListExtra("uriList");
		}
		if (getIntent().hasExtra("type")) {
			type = getIntent().getIntExtra("type", 0);
		}
		adapter = new ImagePagerAdapter(this, uriList, type);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);

		tvTitle.setText((position + 1) + "/" + uriList.size());

		if (type != 0) {
			findViewById(R.id.more).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void initView() {
		tvTitle = (TextView) findViewById(R.id.title);

		View back = findViewById(R.id.back);
		back.setOnClickListener(this);

		View menu = findViewById(R.id.more);
		menu.setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setOnPageChangeListener(this);

	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.more:
			saveDialog = TXDialogUtil.showTwoBtnDialog(this, "提示", "是否保存到本地？", new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.yes:
						imageLoader.loadImage(uriList.get(viewPager.getCurrentItem()), ImagePreviewActivity.this);
						saveDialog.dismiss();
						break;
					case R.id.no:
						saveDialog.dismiss();
						break;
					default:
						break;
					}
				}
			});

			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tvTitle.setText((position + 1) + "/" + uriList.size());
	}

	@Override
	public void onLoadingStarted(String imageUri, View view) {

	}

	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (loadedImage != null) {
			String filename = System.currentTimeMillis() + ".jpg";
			String path = TXFileUtils.saveBitmap(loadedImage, Constants.IMAGE_DIR, filename);
			Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			scanIntent.setData(Uri.fromFile(new File(path)));
			sendBroadcast(scanIntent);
			if (path != null) {
				Toast.makeText(this, "图片保存至" + new File(path).getAbsolutePath(), Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {

	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

}
