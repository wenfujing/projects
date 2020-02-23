package com.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.appcompat.app.AppCompatActivity;

//������Ϣ����
public class MySharedpreferrence {
	private SharedPreferences sp;
	private Editor editor;

	public MySharedpreferrence(Context context) {
		sp = context.getSharedPreferences("Person", AppCompatActivity.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setPerson(String userid) {

		editor.putString("userid", userid);
		editor.commit();
	}

	public String getPerson() {
		String user_id = sp.getString("userid", "");
		if (user_id != null) {
			return user_id;
		} else {
			return "";
		}
	}
}
