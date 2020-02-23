package com.example.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.config.Urls;
import com.dialog.ResultDialog;
import com.dialog.UpdateDialog;
import com.example.project1.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.recorder.AudioFileFunc;
import com.recorder.AudioRecordFunc;
import com.recorder.ErrorCode;
import com.recorder.PlayAudioTrack;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;

public class RecorderFragment extends Fragment {
	// ����¼���ϴ�������ť
	private int mState = -1; // -1:û��¼�ƣ�0��¼��wav
	private final static int FLAG_WAV = 0;
	private LinearLayout huiTing_bg;
	private LinearLayout luYin_bg;
	private LinearLayout shangChuan_bg;
	private TextView huiTing_text;
	private TextView luYin_text;
	private TextView shangChuan_text;
	private UIHandler uiHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater
				.inflate(R.layout.recorder_button, container, false);
		uiHandler = new UIHandler();
		initRecorder(view);
		setRecorderListener();
		return view;
	}

	// ����¼���ϴ��ؼ���ʼ��
	private void initRecorder(View view) {
		huiTing_bg = (LinearLayout) view.findViewById(R.id.huiti_bg);
		huiTing_text = (TextView) view.findViewById(R.id.huiti_text);
		luYin_bg = (LinearLayout) view.findViewById(R.id.luyin_bg);
		luYin_text = (TextView) view.findViewById(R.id.luyin_text);
		shangChuan_bg = (LinearLayout) view.findViewById(R.id.shangchuan_bg);
		shangChuan_text = (TextView) view.findViewById(R.id.shangchuan_text);
	}

	private void setRecorderListener() {
		huiTing_bg.setOnClickListener(new HuiTingListener());
		luYin_bg.setOnClickListener(new LuYinListener());
		shangChuan_bg.setOnClickListener(new ShangChuanListener());
	}

	// ����������
	class HuiTingListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// ����¼��
			// Toast.makeText(MovieActivity.this,"huiting",
			// Toast.LENGTH_SHORT).show();
			play();
		}

	}

	// ¼��������
	class LuYinListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(MovieActivity.this,"luYin",
			// Toast.LENGTH_SHORT).show();
			if (luYin_text.getText().equals("¼��")) {
				record(FLAG_WAV);
			} else {
				stop();
			}

		}

	}

	// �ϴ�������
	class ShangChuanListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(MovieActivity.this,"shangChuan",
			// Toast.LENGTH_SHORT).show();

			getResult();
		}

	}

	private final static int CMD_RECORDING_TIME = 2000;
	private final static int CMD_RECORDFAIL = 2001;
	private final static int CMD_STOP = 2002;
	private final static int CMD_PLAYFAIL = 2003;

	class UIHandler extends Handler {
		public UIHandler() {
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// Log.d("MyHandler", "handleMessage......");
			super.handleMessage(msg);
			Bundle b = msg.getData();
			int vCmd = b.getInt("cmd");
			switch (vCmd) {
			case CMD_RECORDING_TIME:
				luYin_text.setText("���");
				break;
			case CMD_RECORDFAIL:
				int vErrorCode = b.getInt("msg");
				String vMsg = ErrorCode.getErrorInfo(getActivity(), vErrorCode);
				Toast.makeText(getActivity(), "¼��ʧ��", Toast.LENGTH_SHORT)
						.show();
				luYin_text.setText("¼��");
				break;
			case CMD_STOP:
				int vFileType = b.getInt("msg");
				switch (vFileType) {
				case FLAG_WAV:
					AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
					luYin_text.setText("¼��");
					break;
				}
				break;
			case CMD_PLAYFAIL:
				Toast.makeText(getActivity(), "����¼����", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ��ʼ¼��
	 * 
	 * @param mFlag
	 *            ��0��¼��wav��ʽ��1��¼��amr��ʽ
	 */
	private void record(int mFlag) {
		if (mState != -1) {
			Log.d("ces", "ggggg");
			Message msg = new Message();
			Bundle b = new Bundle();// �������
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", ErrorCode.E_STATE_RECODING);
			msg.setData(b);

			uiHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			return;
		}
		int mResult = -1;
		AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
		mResult = mRecord_1.startRecordAndFile();
		if (mResult == ErrorCode.SUCCESS) {
			Log.d("ces", "ssss");
			Message msg = new Message();
			Bundle b = new Bundle();// �������
			b.putInt("cmd", CMD_RECORDING_TIME);
			msg.setData(b);
			uiHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			mState = mFlag;
		} else {
			Log.d("ces", "hhhhh");
			Message msg = new Message();
			Bundle b = new Bundle();// �������
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", mResult);
			msg.setData(b);
			uiHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
		}
	}

	/**
	 * ֹͣ¼��
	 */
	private void stop() {
		if (mState != -1) {
			AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
			mRecord_1.stopRecordAndFile();
			Message msg = new Message();
			Bundle b = new Bundle();// �������
			b.putInt("cmd", CMD_STOP);
			b.putInt("msg", mState);
			msg.setData(b);
			uiHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			mState = -1;
		}
	}

	/**
	 * ����¼��
	 */
	private void play() {
		if (mState != -1) {

			Message msg = new Message();
			Bundle b = new Bundle();// �������
			b.putInt("cmd", CMD_PLAYFAIL);
			b.putInt("msg", mState);
			msg.setData(b);
			uiHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
			mState = -1;
		} else {
			if (AudioFileFunc.getWavFilePath() != "") {
				try {
					PlayAudioTrack.PlayAudioTrack(AudioFileFunc
							.getWavFilePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Log.d("play", "�Ҳ���¼���ļ�������");
			}
		}
	}

	// չʾ����
	public void getResult() {
		UpdateDialog.Builder builder = new UpdateDialog.Builder(getActivity());
		final Dialog updateDialog = builder.create();
		final ResultDialog.Builder builder2 = new ResultDialog.Builder(
				getActivity());
		updateDialog.show();
		// ��ȡ�ϴ��ļ���·��
		String path = AudioFileFunc.getWavFilePath();
		// �ж��ϴ�·���Ƿ�Ϊ��
		if (TextUtils.isEmpty(path.trim())) {
//			Toast.makeText(getActivity(), "�ϴ��ļ�·������Ϊ��", 1).show();
		} else {
			// �첽�Ŀͻ��˶���
			AsyncHttpClient client = new AsyncHttpClient();
			// ָ��url·��
			String url = Urls.UPLOAD;
			// ��װ�ļ��ϴ��Ĳ���
			RequestParams params = new RequestParams();
			// ����·�������ļ�
			File file = new File(path);
			try {
				// �����ļ�
				params.put("file", file);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("file", "�ļ�������----------");
			}
			client.post(url, params, new TextHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					if (statusCode == 200) {
						Log.d("sucess", responseString);
						if(!responseString.equals("0")){
						updateDialog.dismiss();					
						Dialog resultDialog = builder2.create(responseString.trim());
						resultDialog.show();
						}
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub

					Log.d("fail", responseString);
				}
			});
			Log.d("finish", "333333");
		}

	}
}
