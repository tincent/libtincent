/**
 * 
 */
package com.tincent.demo.activity;

import java.util.Date;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.util.TXToastUtil;
import com.tincent.dao.NoteRecord;
import com.tincent.demo.R;
import com.tincent.demo.manager.DaoManager;

/**
 * 添加或修改日记
 * 
 * @author hui.wang
 * 
 */
public class AddNoteActivity extends BaseActivity {
	private EditText edtTitle;
	private EditText edtAuthor;
	private EditText edtContent;

	private TextView txtTitle;
	private NoteRecord record;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btnSubmit:
			submit();
			break;
		}
	}

	private void submit() {
		String title = edtTitle.getText().toString().trim();
		String author = edtAuthor.getText().toString().trim();
		String content = edtContent.getText().toString().trim();

		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(content)) {
			TXToastUtil.getInstatnce().showMessage("输入不能为空");
			return;
		}

		if (record != null) {
			record.setNoteTitle(title);
			record.setNoteAuthor(author);
			record.setNoteContent(content);
			record.setUpdateTime(new Date(System.currentTimeMillis()));
			record.setTimeStamp(System.currentTimeMillis());
			DaoManager.getInstance().getDaoSession().update(record);
			finish();
		} else {
			record = new NoteRecord();
			record.setNoteTitle(title);
			record.setNoteAuthor(author);
			record.setNoteContent(content);
			record.setUpdateTime(new Date(System.currentTimeMillis()));
			record.setCreateTime(new Date(System.currentTimeMillis()));
			record.setTimeStamp(System.currentTimeMillis());
			DaoManager.getInstance().getDaoSession().insert(record);
			finish();
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
		setContentView(R.layout.activity_add_note);

	}

	@Override
	public void initData() {
		if (getIntent().hasExtra("NoteRecord")) {
			record = (NoteRecord) getIntent().getSerializableExtra("NoteRecord");
			txtTitle.setText("修改日记");
			edtTitle.setText(record.getNoteTitle());
			edtAuthor.setText(record.getNoteAuthor());
			edtContent.setText(record.getNoteContent());
		} else {
			txtTitle.setText("创建日记");
		}
	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btnSubmit).setOnClickListener(this);

		txtTitle = (TextView) findViewById(R.id.title);
		edtTitle = (EditText) findViewById(R.id.edtTitle);
		edtAuthor = (EditText) findViewById(R.id.edtAuthor);
		edtContent = (EditText) findViewById(R.id.edtContent);

	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
