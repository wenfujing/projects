package com.tone;

import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itingshuo.MovieActivity;
import com.example.itingshuo.ResultActivity;
import com.example.itingshuo.ToneActivity;
import com.example.project1.R;
import com.tool.MySharedpreferrence;

public class ToneListAdapter extends ArrayAdapter<ToneList> {
    private int resource;
	public ToneListAdapter(Context context, int resource, List<ToneList> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resource = resource;//resourceΪlistView��ÿ������Ĳ���id
		
	}
	//getViewΪlistView��ÿ������Ĳ�����������
	//convertView���ڽ�֮ǰ���غõĲ��ֽ��л���
	//����һ��viewHolder�Կؼ����л���
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		
//		Fruit fruit = getItem(position);//���ʵ��
//		View view;
//		if(convertView==null){
//		 view = LayoutInflater.from(getContext()).inflate(resource, null);
//		}else {
//			view = convertView;
//		}
//		ImageView fruitImage =  (ImageView) view.findViewById(R.id.fruit_image);
//		TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
//		fruitImage.setImageResource(fruit.getImageId());
//		fruitName.setText(fruit.getName());
//		return view;
		
		
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
		 view = LayoutInflater.from(getContext()).inflate(resource, null);
		 viewHolder = new ViewHolder();
		 viewHolder.toneTitle =(TextView) view.findViewById(R.id.tv_tone_title);
		 viewHolder.toneTime =(TextView) view.findViewById(R.id.tv_tone_time);
		 viewHolder.toneFinish = (TextView) view.findViewById(R.id.tv_tone_finish);
		 viewHolder.toneBeginStudy = (ImageView) view.findViewById(R.id.img_tone_beginStudy);
		 viewHolder.toneLookResult = (ImageView) view.findViewById(R.id.img_tone_lookresult);
		 view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		final ToneList classList = getItem(position);//���ʵ��,final���ܱ�onclick���ڲ������ã�position��Ҫ������Ҳ��Ҫ��final
		if(classList!=null){
		viewHolder.toneTitle.setText(classList.getTitle());
		viewHolder.toneTime.setText(classList.getTime());
		viewHolder.toneFinish.setText(classList.getFinish());
		if(classList.getFinish().equals("�����")){
			//setTextColor(R.color.finish_blue)��Ч
			viewHolder.toneFinish.setTextColor(getContext().getResources().getColor(R.color.finish_blue));//û���ã�
		}else{//һ��Ҫдelse����δ��ɣ���Ȼ�������б�ʱ�����б���ɫ��λ
			viewHolder.toneFinish.setTextColor(getContext().getResources().getColor(R.color.finish_No));
		}
		//����ѧϰ������
		viewHolder.toneBeginStudy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mySendIntent(classList.getToneid(),classList.getTypeid());
			}
		});
		//�鿴�ɼ�������
		viewHolder.toneLookResult.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(getContext(), classList.getTitle()+"LookResult", Toast.LENGTH_SHORT).show();
						MySharedpreferrence sp = new MySharedpreferrence(getContext());
						String username = sp.getPerson();
						Log.d("username", "username:"+username);
						Intent intent = new Intent(getContext(), ResultActivity.class);
						 getContext().startActivity(intent);
					}
				});
		}
		
		return view;
		
	}
class ViewHolder{
	TextView toneTitle;
	TextView toneTime;
	TextView toneFinish;
	ImageView toneBeginStudy;
	ImageView toneLookResult;
}
public void mySendIntent(String typeid,String toneid){
	Intent intent = new Intent(getContext(), ToneActivity.class);
    //newһ��Bundle���󣬲���Ҫ���ݵ����ݴ���
    Bundle bundle = new Bundle();
   bundle.putString("typeid", typeid);
   bundle.putString("toneid", toneid);
   Log.d("send", ""+typeid);
   Log.d("send", ""+toneid);
    //��bundle����assign��Intent
    intent.putExtras(bundle);
    //������ת
    getContext().startActivity(intent);
}
}