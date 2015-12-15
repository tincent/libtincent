/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tincent.android.adapter.TXAbsAdapter;
import com.tincent.demo.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 数字适配器
 * 
 * @author hui.wang
 * @date 2015.12.14
 *
 */
public class NumberAdapter extends TXAbsAdapter {
	private List<String> nums;

	public NumberAdapter(Context ctx) {
		super(ctx);
		nums = new ArrayList<String>();
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");
	}

	@Override
	public int getCount() {
		if (nums != null) {
			return nums.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (nums != null) {
			return nums.get(position);
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
			convertView = getLayoutInflater().inflate(R.layout.app_list_item, null);
			holder.num = (TextView) convertView.findViewById(R.id.txtNum);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layoutLinear);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.num.setText(nums.get(position));
		
		return convertView;
	}

	public class ViewHolder {
		public LinearLayout layout;
		public TextView num;
	}

}
