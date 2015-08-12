/**
 * 
 */
package com.tincent.demo.fragment;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tincent.android.http.TXResponseEvent;
import com.tincent.demo.Constants;
import com.tincent.demo.R;
import com.tincent.demo.activity.WebviewActivity;
import com.tincent.demo.adapter.NewsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author hui.wang
 * 
 */
public class NewsFragment extends BaseFragment implements OnRefreshListener2<ListView>, OnLastItemVisibleListener, OnItemClickListener {
	private TextView tvTitle;
	private PullToRefreshListView newsList;
	private NewsAdapter newsAdpater;
	private View emptyView;

	private static final int MSG_REFRESH_COMPLATE = 1;

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_REFRESH_COMPLATE:
			newsList.onRefreshComplete();
			// 模拟网络数据
			newsAdpater.addCount(5);
			break;
		}
		return false;
	}

	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_news, container, false);
	}

	public void initView(View rootView) {
		rootView.findViewById(R.id.back).setVisibility(View.GONE);
		tvTitle = (TextView) rootView.findViewById(R.id.title);
		tvTitle.setText("资讯中心");

		emptyView = rootView.findViewById(R.id.empty);

		newsList = (PullToRefreshListView) rootView.findViewById(R.id.newslist);
		newsList.setOnItemClickListener(this);
		newsList.setMode(Mode.BOTH);
		newsList.setScrollingWhileRefreshingEnabled(false);
		newsList.setOnRefreshListener(this);
		newsList.setOnLastItemVisibleListener(this);
		newsList.setEmptyView(emptyView);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initData() {
		newsAdpater = new NewsAdapter(getActivity());
	}

	@Override
	public void updateView() {
		newsList.setAdapter(newsAdpater);
	}

	@Override
	public void onLastItemVisible() {

	}

	// 下拉请求网络数据
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 假设5秒后数据请求成功
		mHandler.sendEmptyMessageDelayed(MSG_REFRESH_COMPLATE, 2000);
	}

	// 上拉请求网络数据
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 假设5秒后数据请求成功
		mHandler.sendEmptyMessageDelayed(MSG_REFRESH_COMPLATE, 2000);
	}

	@Override
	public void onResponseSuccess(TXResponseEvent evt) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), WebviewActivity.class);
		intent.putExtra(Constants.KEY_WEBVIEW_TITLE, "听讯科技");
		intent.putExtra(Constants.KEY_WEB_URL, "http://www.tincent.me");
		startActivity(intent);
	}

}
