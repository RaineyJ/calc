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
 * @info ������CALC
 */
public class CalcActivity extends Activity {

	GridLayout gridlayout;
	// ����16��ť�ı�
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
			// ���ð�ť�ֺŴ�С
			bn.setTextSize(40);
			// ���ð�ť���ܿհ�����
			bn.setPadding(5, 35, 5, 35);

			// �趨������
			bn.setOnClickListener(new MyListener());

			// ָ�������������
			GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);
			// ָ�������������
			GridLayout.Spec columnSpec = GridLayout.spec(i % 4);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(
					rowSpec, columnSpec);
			// ָ�������ռ��������
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

		// ����ַ���
		char[] c = input.toCharArray();
		// list���ڴ洢�����λ��
		List<Integer> breaker = new ArrayList<Integer>();
		// ͳ�������������λ��
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
		// ��ȡ�������� �ַ�������
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

		// ���ַ�������ת��Ϊ����
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
