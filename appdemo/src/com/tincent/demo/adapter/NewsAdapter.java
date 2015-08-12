/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.List;

import com.tincent.android.adapter.TXAbsAdapter;
import com.tincent.demo.R;
import com.tincent.demo.model.NewsBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 资讯中心适配器
 * 
 * @author hui.wang
 * @date 2015.6.16
 * 
 */
public class NewsAdapter extends TXAbsAdapter {
	private List<NewsBean> newsList;
	private int count = 10;
	
	public void addCount(int delta) {
		count += delta;
		notifyDataSetChanged();
	}
	
	public NewsAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public int getCount() {
		if (newsList != null) {
			return newsList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		if (newsList != null) {
			return newsList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * NewsBean bean = newsList.get(position);
		 * 
		 * if(position % 5 == 0) { bean.msgType = 0; } else { bean.msgType = 1;
		 * }
		 */

		if (position % 5 == 0) {
			convertView = getLayoutInflater().inflate(R.layout.news_list_item_header, null);
		} else {
			convertView = getLayoutInflater().inflate(R.layout.news_list_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		ImageView imgAlbum = (ImageView) convertView.findViewById(R.id.imgAlbum);

		// txtTitle.setText(bean.msgTitle);
		// imgAlbum.setImageResource(R.drawable.banner_stub);

		return convertView;
	}

}
