/**
 * 
 */
package com.tincent.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tincent.android.adapter.TXAbsAdapter;
import com.tincent.android.util.TXTimeUtil;
import com.tincent.dao.NoteRecord;
import com.tincent.demo.R;

/**
 * 日记适配器
 * 
 * @author hui.wang
 * 
 */
public class NotesAdapter extends TXAbsAdapter {
	private List<NoteRecord> recordList;

	/**
	 * @param ctx
	 */
	public NotesAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public int getCount() {
		if (recordList != null) {
			return recordList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (recordList != null) {
			return recordList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NoteRecord record = recordList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = getLayoutInflater().inflate(R.layout.note_list_item, null);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			holder.txtAuthor = (TextView) convertView.findViewById(R.id.txtAuthor);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(record.getNoteTitle());
		holder.txtAuthor.setText(record.getNoteAuthor());
		holder.txtTime.setText(TXTimeUtil.convert2String(record.getTimeStamp()));
		return convertView;
	}

	public class ViewHolder {
		public TextView txtTitle;
		public TextView txtAuthor;
		public TextView txtTime;
	}
	
	public void updateData(List<NoteRecord> data) {
		recordList = data;
		notifyDataSetChanged();
	}

}
