package com.result;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itingshuo.MovieActivity;
import com.example.itingshuo.ToneActivity;
import com.example.project1.R;

public class ResultAdapter extends ArrayAdapter<ResutlList> {
    private int resource;
	public ResultAdapter(Context context, int resource, List<ResutlList> objects) {
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
		 viewHolder.resultGrade =(TextView) view.findViewById(R.id.tv_result_grade);
		 viewHolder.resultPingyu =(TextView) view.findViewById(R.id.tv_result_pingyu2);
		 viewHolder.resultDate = (TextView) view.findViewById(R.id.tv_result_date);
		
		 view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		final ResutlList resutlList = getItem(position);//���ʵ��,final���ܱ�onclick���ڲ������ã�position��Ҫ������Ҳ��Ҫ��final
		if(resutlList!=null){
		viewHolder.resultGrade.setText(resutlList.getGrade());
		viewHolder.resultPingyu.setText(resutlList.getPingyu());
		viewHolder.resultDate.setText(resutlList.getDate());
		}
		
		return view;
		
	}
class ViewHolder{
	TextView resultGrade;
	TextView resultPingyu;
	TextView resultDate;
}

}