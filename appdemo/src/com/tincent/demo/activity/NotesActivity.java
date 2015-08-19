/**
 * 
 */
package com.tincent.demo.activity;

import java.util.List;

import android.content.Intent;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.dao.NoteRecord;
import com.tincent.demo.R;
import com.tincent.demo.adapter.NotesAdapter;
import com.tincent.demo.manager.DaoManager;
import com.tincent.demo.util.Debug;

/**
 * 日记列表界面
 * 
 * @author hui.wang
 * 
 */
public class NotesActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener {
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_UPDATE = Menu.FIRST + 1;

	private ListView listView;
	private View emptyView;
	private NotesAdapter adapter;
	private List<NoteRecord> records;
	private int position;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.more:
			Intent intent = new Intent(this, AddNoteActivity.class);
			startActivity(intent);
			break;
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_notes);

	}

	@Override
	public void initData() {
		adapter = new NotesAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnCreateContextMenuListener(this);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("日记列表");
		ImageButton menu = (ImageButton) findViewById(R.id.more);
		menu.setImageResource(R.drawable.icon_add);
		menu.setVisibility(View.VISIBLE);
		menu.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.lvNoteList);
		emptyView = findViewById(R.id.stub_tip);
		listView.setEmptyView(emptyView);

	}

	@Override
	public void updateView() {
		records = DaoManager.getInstance().getDaoSession().getNoteRecordDao().loadAll();
		adapter.updateData(records);
	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		Debug.o(new Exception());
		menu.add(0, MENU_DELETE, 0, "删除");
		menu.add(0, MENU_UPDATE, 0, "修改");
		
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		NoteRecord record = (NoteRecord) adapter.getItem(position);
		switch (item.getItemId()) {
		case MENU_DELETE:
			DaoManager.getInstance().getDaoSession().getNoteRecordDao().delete(record);
			records = DaoManager.getInstance().getDaoSession().getNoteRecordDao().loadAll();
			adapter.updateData(records);
			break;
		case MENU_UPDATE:
			Intent intent = new Intent(this, AddNoteActivity.class);
			intent.putExtra("NoteRecord", record);
			startActivity(intent);
			break;
		}
		return super.onContextItemSelected(item);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		this.position = position;
		listView.showContextMenu();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NoteRecord record = (NoteRecord) adapter.getItem(position);
		Intent intent = new Intent(this, AddNoteActivity.class);
		intent.putExtra("NoteRecord", record);
		startActivity(intent);
	}

}
