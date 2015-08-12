/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.List;

import com.tincent.android.view.photoview.PhotoViewAttacher;
import com.tincent.demo.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 图片展示适配器
 * 
 * @author hui.wang
 * @date 2015.5.26
 * 
 */
public class ImageDisplayAdapter extends PagerAdapter {
	private Context context;
	private List<String> uriList;
	private LayoutInflater inflater;
	private int type = 0;// 0本地 1网络
	private BitmapUtils bitmapUtils;

	public ImageDisplayAdapter(Context ctx, List<String> uris, int type) {
		bitmapUtils = new BitmapUtils(ctx);
		bitmapUtils.configDefaultLoadingImage(R.drawable.banner_stub);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.banner_stub);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		context = ctx;
		inflater = LayoutInflater.from(ctx);
		uriList = uris;
		this.type = type;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.image_pager_item, view, false);
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
		String uri = uriList.get(position);
		if (type == 0) {
			uri = "file://" + uri;
		}
		final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);

		bitmapUtils.display(imageView, uri, new DefaultBitmapLoadCallBack<ImageView>() {
			@Override
			public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
				spinner.setVisibility(View.VISIBLE);
				// Debug.o(new Exception());
			}

			@Override
			public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
				spinner.setVisibility(View.GONE);
				//container.setImageBitmap(bitmap);
				fadeInDisplay(container, bitmap);
				mAttacher.update();
			}

			@Override
			public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
				spinner.setVisibility(View.GONE);
			}
		});

		view.addView(imageLayout, 0);
		return imageLayout;
	}

	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

	/**
	 * @author sunglasses
	 * @category 图片加载效果
	 * @param imageView
	 * @param bitmap
	 */
	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {// 目前流行的渐变效果
		final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] { TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return uriList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
