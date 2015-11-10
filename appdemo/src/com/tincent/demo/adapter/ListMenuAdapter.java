/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tincent.android.adapter.TXAbsAdapter;
import com.tincent.demo.R;

/**
 * 列表菜单适配器
 * 
 * @author hui.wang
 * @date 2015.11.10
 * 
 */
public class ListMenuAdapter extends TXAbsAdapter {
	private List<ApplicationInfo> applist;

	public ListMenuAdapter(Context ctx) {
		super(ctx);
		applist = ctx.getPackageManager().getInstalledApplications(0);
	}

	@Override
	public int getCount() {
		if (applist != null) {
			return applist.size();
		}
		return 0;
	}

	@Override
	public ApplicationInfo getItem(int position) {
		if (applist != null) {
			return applist.get(position);
		}
		return null;
	}
	
	public void remove(int position) {
		if(applist != null) {
			applist.remove(position);
			notifyDataSetChanged();
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = getLayoutInflater().inflate(R.layout.swipe_list_item, null);
			holder.logo = (ImageView) convertView.findViewById(R.id.app_logo);
			holder.name = (TextView) convertView.findViewById(R.id.app_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ApplicationInfo item = getItem(position);
		holder.logo.setImageDrawable(item.loadIcon(getContext().getPackageManager()));
		holder.name.setText(item.loadLabel(getContext().getPackageManager()));
		return convertView;
	}

	public class ViewHolder {
		public ImageView logo;
		public TextView name;
	}

}
