package com.minder.gotandroid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.minder.gotandroid.R;


public class CustomProgressDialog extends Dialog{
	public CustomProgressDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ��������(?) ���̾� �α� ������ ����
		setContentView(R.layout.custom_progress_dialog); // ���̾��α׿� ���� ���̾ƿ�
	}
}