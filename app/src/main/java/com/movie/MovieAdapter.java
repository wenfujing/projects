package com.movie;

import java.util.List;

import volley.VolleyManager;

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
import com.example.itingshuo.MovieListActivity;
import com.example.project1.R;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private int resource;
	public MovieAdapter(Context context, int resource, List<Movie> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resource = resource;//resourceΪlistView��ÿ������Ĳ���id
		
	}
	//getViewΪlistView��ÿ������Ĳ�����������
	//convertView���ڽ�֮ǰ���غõĲ��ֽ��л���
	//����һ��viewHolder�Կؼ����л���
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
		
		final Movie movie = getItem(position);//���ʵ��
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
		 view = LayoutInflater.from(getContext()).inflate(resource, null);
		 viewHolder = new ViewHolder();
		 viewHolder.movieImage= (ImageView) view.findViewById(R.id.img_movie);
		 viewHolder.movieTitle =(TextView) view.findViewById(R.id.tv_title);
		 viewHolder.movieContent =(TextView) view.findViewById(R.id.tv_content);
		 viewHolder.movieTime = (TextView) view.findViewById(R.id.tv_time);
	     viewHolder.movieBeginStudy = (ImageView) view.findViewById(R.id.img_movie_beginStudy);
		 view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		 VolleyManager.newInstance().ImageLoaderRequest(viewHolder.movieImage, movie.getImgSrc(),
	                R.drawable.ic_default, R.drawable.ic_error, 80, 80);
		viewHolder.movieTitle.setText(movie.getTitle());
		viewHolder.movieContent.setText(movie.getContent());
		viewHolder.movieTime.setText(movie.getTime());
	
		//����ѧϰ������
				viewHolder.movieBeginStudy.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(getContext(), movie.getTitle()+"begin study", Toast.LENGTH_SHORT).show();
						mySendIntent(movie.getMovieid(),movie.getEmotionid());
					}
				});
		return view;
		
	}
class ViewHolder{
	TextView movieContent;
	ImageView movieImage;
	TextView movieTitle;
	TextView movieTime;
	ImageView movieBeginStudy;
}
public void mySendIntent(String movieid,String emotionid){
	Intent intent = new Intent(getContext(), MovieActivity.class);
//    //newһ��Bundle���󣬲���Ҫ���ݵ����ݴ���
  Bundle bundle = new Bundle();
    bundle.putString("movieid", movieid);
    bundle.putString("emotionid", emotionid);
    Log.d("sendbundle","emotionid: "+emotionid);
	Log.d("sendbundle","movieid: "+movieid);
    //��bundle����assign��Intent
    intent.putExtras(bundle);
//    //������ת
    getContext().startActivity(intent);
}
}
