/**
 * 
 */
package com.tincent.demo.view;

import com.tincent.demo.R;
import com.tincent.demo.util.Debug;
import com.tincent.demo.util.DensityUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 实现特定列表功能
 * 
 * @author hui.wang
 * @date 2015.12.15
 *
 */
public class CustomListView extends ListView {
	public CustomListView(Context context) {
		super(context);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.color_7c7c82));
		paint.setAntiAlias(true);
		paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2));
		int startX = DensityUtil.dip2px(getContext(), 30);
		int stopY = getHeight();
		// Debug.o(new Exception(), "startX = " + startX + ", stopY = " +
		// stopY);
		canvas.drawLine(startX, 0, startX, stopY, paint);
		super.onDraw(canvas);
	}

	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
