/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.List;

import com.tincent.android.view.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tincent.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 图片预览适配器
 * 
 * @author hui.wang
 * @date 2015.5.14
 * 
 */
public class ImagePagerAdapter extends PagerAdapter {
	private Context context;
	private List<String> uriList;
	private LayoutInflater inflater;
	private int type = 0;// 0本地 1网络
	private ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.banner_stub).showImageOnFail(R.drawable.banner_stub).resetViewBeforeLoading(true)
			.cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();

	public ImagePagerAdapter(Context ctx, List<String> uris, int type) {
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
		imageLoader.displayImage(uri, imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// int message = 0;
				// switch (failReason.getType()) {
				// case IO_ERROR:
				// message = R.string.io_error;
				// break;
				// case DECODING_ERROR:
				// message = R.string.decode_error;
				// break;
				// case NETWORK_DENIED:
				// message = R.string.network_error;
				// break;
				// case OUT_OF_MEMORY:
				// message = R.string.out_of_memory;
				// break;
				// case UNKNOWN:
				// message = R.string.unknown_error;
				// break;
				// }
				// Toast.makeText(context, message, Toast.LENGTH_SHORT)
				// .show();

				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
				mAttacher.update();
				// Debug.o(new Exception(), "imageUri = " + imageUri);
			}

		});

		view.addView(imageLayout, 0);
		return imageLayout;
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
