package com.tincent.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tincent.demo.R;
import com.tincent.demo.activity.ImageDisplayActivity;
import com.tincent.demo.activity.ImagePreviewActivity;
import com.tincent.demo.model.AdBean;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 广告条适配器
 * 
 * @author hui.wang
 * @date 2015.3.25
 * 
 */
public class AdBannerAdapter extends PagerAdapter {
	private DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.banner_stub).showImageOnFail(R.drawable.banner_stub)
			.showImageOnLoading(R.drawable.banner_stub).resetViewBeforeLoading(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();
	private List<AdBean> adList;

	private LayoutInflater inflater;

	private Context context;

	private ImageLoader imageLoader = ImageLoader.getInstance();

	public AdBannerAdapter(Context ctx) {
		context = ctx;
		inflater = LayoutInflater.from(ctx);
		adList = new ArrayList<AdBean>();
		AdBean bean = new AdBean();
		bean.imgul = "http://www.wahh.com.cn/UploadFiles/UploadADPic/201407291041466579.jpg";
		//bean.httpurl = "http://mhospital.yihu.com/hospital/default.aspx?hospitalid=734&platformType=0&sourceType=1&sourceId=734";

		AdBean bean1 = new AdBean();
		bean1.imgul = "http://www.wahh.com.cn/UploadFiles/UploadADPic/201401271110367821.jpg";
		//bean1.httpurl = "http://88.88.88.197:8010/Summery/index.html?PatientId=108";

		AdBean bean2 = new AdBean();
		bean2.imgul = "http://www.wahh.com.cn/UploadFiles/UploadADPic/201404090916310760.jpg";
		//bean2.httpurl = "http://88.88.88.197:8010/ForTestRecover/index.html";

		adList.add(bean);
		adList.add(bean1);
		adList.add(bean2);

	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.image_pager_item, view, false);
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		final int pos = position;
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ImagePreviewActivity.class);
				intent.putExtra("position", pos);
				intent.putStringArrayListExtra("uriList", (ArrayList<String>) getUriList());
				context.startActivity(intent);
			}
		});

		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
		imageLoader.displayImage(adList.get(position).imgul, imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
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
		if (adList != null) {
			return adList.size();
		}
		return 0;
	}

	public String getItem(int position) {
		if (adList != null) {
			return adList.get(position).httpurl;
		}
		return null;
	}
	
	public List<String> getUriList() {
		if(adList != null) {
			List<String> uriList = new ArrayList<String>();
			for(AdBean bean : adList) {
				uriList.add(bean.imgul);
			}
			return uriList;
		}
		return null;
	}

	public void updateDate(List<AdBean> list) {
		adList = list;
		notifyDataSetChanged();
	}
}
