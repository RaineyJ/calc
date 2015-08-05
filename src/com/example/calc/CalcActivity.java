package com.example.calc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * @author Jy
 * @info 计算器CALC
 */
public class CalcActivity extends Activity {

	GridLayout gridlayout;
	// 定义16按钮文本
	String[] chars = new String[] { "7", "8", "9", "/", "4", "5", "6", "*",
			"1", "2", "3", "-", ".", "0", "=", "+" };

	StringBuffer sb = new StringBuffer();

	// Button[] bns = new Button[16];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calc);
		gridlayout = (GridLayout) findViewById(R.id.calc);
		for (int i = 0; i < chars.length; i++) {
			Button bn = new Button(this);
			bn.setText(chars[i]);
			// 设置按钮字号大小
			bn.setTextSize(40);
			// 设置按钮四周空白区域
			bn.setPadding(5, 35, 5, 35);

			// 设定监听器
			bn.setOnClickListener(new MyListener());

			// 指定该组件所在行
			GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);
			// 指定该组件所在列
			GridLayout.Spec columnSpec = GridLayout.spec(i % 4);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(
					rowSpec, columnSpec);
			// 指定该组件占满父容器
			params.setGravity(Gravity.FILL);
			gridlayout.addView(bn, params);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity5, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String calc(String input) {

		// 拆分字符串
		char[] c = input.toCharArray();
		// list用于存储运算符位置
		List<Integer> breaker = new ArrayList<Integer>();
		// 统计运算符个数和位置
		for (int i = 0; i < c.length; i++) {
			switch (c[i]) {
			case '+':
			case '-':
			case '*':
			case '/':
				breaker.add(i);
				break;
			default:
				break;
			}
		}
		// 获取运算数字 字符串数组
		String[] numbs = new String[breaker.size() + 1];
		int beg = 0;
		int end = 0;
		for (int j = 0; j < breaker.size(); j++) {

			end = breaker.get(j);
			numbs[j] = input.substring(beg, end);
			beg = end + 1;
		}
		numbs[breaker.size()] = input
				.substring(breaker.get(breaker.size() - 1) + 1);
		for (String s : numbs) {
			System.out.println(s);
		}

		// 将字符串数组转化为数字
		int[] bs = new int[numbs.length];
		List<Integer> nl = new ArrayList<Integer>();
		for (int k = 0; k < numbs.length; k++) {
			nl.add(Integer.parseInt(numbs[k]));
		}
		while (!breaker.isEmpty()) {
			switch (c[breaker.get(0)]) {
			case '+': {

				int temp = nl.get(nl.size() - 1) + nl.get(nl.size() - 2);
				nl.remove(nl.size() - 1);
				nl.remove(nl.size() - 1);
				nl.add(temp);
				breaker.remove(0);
				break;
			}
			case '-':
				int temp = nl.get(nl.size() - 1) - nl.get(nl.size() - 2);
				nl.remove(nl.size() - 1);
				nl.remove(nl.size() - 1);
				nl.add(temp);
				breaker.remove(0);
				break;
			default:
				break;
			}
		}
		return nl.get(0).toString();
	}

	class MyListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button bn = (Button) v;
			String s = bn.getText().toString();

			if (s == "=") {
				String input = sb.toString();
				sb.delete(0, sb.length());
				// String res = calc(input);
				TextView tv = (TextView) findViewById(R.id.result);
				tv.setText(input);

			} else {
				sb.append(s);
				TextView tv = (TextView) findViewById(R.id.result);
				tv.setText(sb);
			}

		}

	}
}
