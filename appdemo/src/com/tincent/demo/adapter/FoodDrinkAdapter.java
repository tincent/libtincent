package com.tincent.demo.adapter;

import java.util.List;

import com.tincent.android.adapter.TXAbsAdapter;
import com.tincent.demo.R;
import com.tincent.demo.model.TextBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 饮食意见适配器
 * 
 * @author jiawei.xue
 * 
 *         2015-8-12 上午10:45:33
 */
public class FoodDrinkAdapter extends TXAbsAdapter {
	private List<TextBean> advises;

	public FoodDrinkAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public int getCount() {
		if (advises != null) {
			return advises.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (advises != null) {
			return advises.get(position);
		}
		return null;
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
			convertView = getLayoutInflater().inflate(R.layout.food_tips_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.position = (TextView) convertView.findViewById(R.id.position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TextBean advise = advises.get(position);
		holder.text.setText(advise.name);
		holder.position.setText((position + 1) + ".");
		return convertView;
	}

	public void updateDate(List<TextBean> data) {
		advises = data;
		notifyDataSetChanged();
	}

	public class ViewHolder {
		public TextView text;
		public TextView position;
	}
}
