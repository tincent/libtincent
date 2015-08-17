package com.tincent.demo.activity;

import java.util.ArrayList;
import java.util.List;

import com.tincent.android.http.TXResponseEvent;
import com.tincent.android.util.TXToastUtil;
import com.tincent.demo.R;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 饮食意见界面
 * 
 * @author jiawei.xue
 * 
 *         2015-8-11 下午6:19:58
 */
public class FoodAndDrinkActivity extends BaseActivity {
	// 癌症
	private CheckBox cbAiZheng;
	// 冠心病
	private CheckBox cbGuangXinBin;
	// 高血压
	private CheckBox cbGaoXueYa;
	// 糖尿病
	private CheckBox cbTangNiaoBin;
	// 肥胖
	private CheckBox cbFeiPang;
	// 肝功能不全
	private CheckBox cbGanGonNen;
	// 肾功能不全
	private CheckBox cbShenGonNen;
	// 老年女性
	private CheckBox cbLaoFuNv;
	// 换瓣手术
	private CheckBox cbHanBanShouShu;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.submit:
			submit();
			break;

		default:
			break;
		}

	}

	private void submit() {
		List<String> wheres = new ArrayList<String>();
		List<String> args = new ArrayList<String>();
		StringBuilder selection = null;
		String[] selectionArgs = null;
		if (cbAiZheng.isChecked()) {
			wheres.add("CANCER_FLAG = ? ");
			args.add("1");
		} 
		
		if (cbGuangXinBin.isChecked()) {
			wheres.add("CORONARY_FLAG = ? ");
			args.add("1");
		}
		
		if (cbGaoXueYa.isChecked()) {
			wheres.add("BLOODPRESSURE_FLAG = ? ");
			args.add("1");
		}
		
		if (cbTangNiaoBin.isChecked()) {
			wheres.add("DIABETES_FLAG = ? ");
			args.add("1");
		}
		
		if (cbFeiPang.isChecked()) {
			wheres.add("FAT_FLAG = ? ");
			args.add("1");
		}
		
		if (cbGanGonNen.isChecked()) {
			wheres.add("LIVER_FLAG = ? ");
			args.add("1");
		}
		
		if (cbShenGonNen.isChecked()) {
			wheres.add("RENAL_FLAG = ? ");
			args.add("1");
		} 
		
		if (cbLaoFuNv.isChecked()) {
			wheres.add("OLDWOMAN_FLAG = ? ");
			args.add("1");
		}
		
		if (cbHanBanShouShu.isChecked()) {
			wheres.add("VALVE_FLAG = ? ");
			args.add("1");
		}
		
		if(wheres.size() > 0) {
			selection = new StringBuilder();
			
			for(int i = 0; i < wheres.size(); i++) {
				selection.append(wheres.get(i));
				if(i != wheres.size() - 1) {
					selection.append(" or ");
				}
			}
			selectionArgs = args.toArray(new String[args.size()]);
		} else {
			TXToastUtil.getInstatnce().showMessage("您没有选中任何症状！");
			return;
		}
		
		Intent intent = new Intent(this, FoodDrinkActivity.class);
		intent.putExtra("selection", selection != null ? selection.toString() : null);
		intent.putExtra("selectionArgs", selectionArgs);
		startActivity(intent);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_foodanddrink);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("选择症状");
		findViewById(R.id.submit).setOnClickListener(this);

		cbAiZheng = (CheckBox) findViewById(R.id.aiZheng);
		cbGuangXinBin = (CheckBox) findViewById(R.id.guanXinBing);
		cbGaoXueYa = (CheckBox) findViewById(R.id.highXueYa);
		cbTangNiaoBin = (CheckBox) findViewById(R.id.tangNiaoBing);
		cbFeiPang = (CheckBox) findViewById(R.id.feiPang);
		cbGanGonNen = (CheckBox) findViewById(R.id.ganGongNeng);
		cbShenGonNen = (CheckBox) findViewById(R.id.shenGongNeng);
		cbLaoFuNv = (CheckBox) findViewById(R.id.woman);
		cbHanBanShouShu = (CheckBox) findViewById(R.id.shouShu);
	}

	@Override
	public void updateView() {

	}

	@Override
	public boolean handleAsynMsg(Message msg) {
		return false;
	}

	@Override
	public void exitApp() {

	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {
		
	}

}
