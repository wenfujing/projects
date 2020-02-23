package com.example.itingshuo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project1.R;
import com.movie.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChooseActivity extends AppCompatActivity {

	private ImageView movie_begin;
	private ImageView speak_begin;
	private ImageView tone_begin;

	public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // ͼƬ����·��

	private ViewPager adViewPager;
	private List<ImageView> imageViews;// ������ͼƬ����

	private List<View> dots; // ͼƬ�������ĵ���Щ��
	private List<View> dotList;
	// private TextView tv_title;
	// private TextView tv_topic;
	private int currentItem = 0; // ��ǰͼƬ��������
	// ��������ָʾ��
	private View dot0;
	private View dot1;
	private View dot2;
	private View dot3;
	private View dot4;

	// ��ʱ����
	private ScheduledExecutorService scheduledExecutorService;

	// �첽����ͼƬ
	// private ImageLoader mImageLoader;
	// private DisplayImageOptions options;

	// �ֲ�banner������
	private List<Banner> adList;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			adViewPager.setCurrentItem(currentItem);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choose);
		// ʹ��ImageLoader֮ǰ��ʼ��
		// initImageLoader();
		// ��ȡͼƬ����ʵ��
		// mImageLoader = ImageLoader.getInstance();
		// options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.top_banner_android)
		// .showImageForEmptyUri(R.drawable.top_banner_android)
		// .showImageOnFail(R.drawable.top_banner_android)
		// .cacheInMemory(true).cacheOnDisc(true)
		// .bitmapConfig(Bitmap.Config.RGB_565)
		// .imageScaleType(ImageScaleType.EXACTLY).build();
		init();
		setRecorderListener();
		initAdData();

		startAd();
	}

	public void init() {
		movie_begin = (ImageView) findViewById(R.id.img_movie_start);
		speak_begin = (ImageView) findViewById(R.id.img_speak_start);
		tone_begin = (ImageView) findViewById(R.id.img_tone_start);

	}

	private void setRecorderListener() {
		movie_begin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		speak_begin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		tone_begin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	// /**
	// * ��ʼ��ImageLoader
	// */
	// private void initImageLoader() {
	// File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
	// .getOwnCacheDirectory(getApplicationContext(),
	// IMAGE_CACHE_PATH);
	//
	// DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
	// .cacheInMemory(true).cacheOnDisc(true).build();
	//
	// ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
	// this).defaultDisplayImageOptions(defaultOptions)
	// .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
	// .memoryCacheSize(12 * 1024 * 1024)
	// .discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
	// .discCache(new UnlimitedDiscCache(cacheDir))
	// .threadPriority(Thread.NORM_PRIORITY - 2)
	// .tasksProcessingOrder(QueueProcessingType.LIFO).build();
	//
	// ImageLoader.getInstance().init(config);
	// }

	/**
	 * ��ʼ���������
	 */
	private void initAdData() {
		// �������
		adList = getBannerAd();

		imageViews = new ArrayList<ImageView>();

		// ��
		dots = new ArrayList<View>();
		dotList = new ArrayList<View>();
		dot0 = findViewById(R.id.v_dot0);
		dot1 = findViewById(R.id.v_dot1);
		dot2 = findViewById(R.id.v_dot2);
		dots.add(dot0);
		dots.add(dot1);
		dots.add(dot2);
		//
		// tv_title = (TextView) findViewById(R.id.tv_title);
		// tv_topic = (TextView) findViewById(R.id.tv_topic);

		adViewPager = (ViewPager) findViewById(R.id.vp);
		adViewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
		// ����һ������������ViewPager�е�ҳ��ı�ʱ����
		adViewPager.setOnPageChangeListener(new MyPageChangeListener());
		addDynamicView();
	}

	private void addDynamicView() {
		// ��̬���ͼƬ������ָʾ��Բ��
		// ��ʼ��ͼƬ��Դ
		for (int i = 0; i < adList.size(); i++) {
			ImageView imageView = new ImageView(this);
			// �첽����ͼƬ
			// mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
			// options);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(adList.get(i).getImgUrl());
			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE);
			dotList.add(dots.get(i));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void startAd() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// ��Activity��ʾ������ÿ�����л�һ��ͼƬ��ʾ
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
	}

	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (adViewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		scheduledExecutorService.shutdown();
	}

	private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

		private int oldPosition = 0;

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			Banner adDomain = adList.get(position);
			// tv_title.setText(adDomain.getTitle()); // ���ñ���
			// tv_topic.setText(adDomain.getTopic());
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return adList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = imageViews.get(position);
			((ViewPager) container).addView(iv);
			final Banner adDomain = adList.get(position);
			// �����������������ͼƬ�ĵ���¼�
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ������ת�߼�
				}
			});
			return iv;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	/**
	 * �ֲ��㲥ģ������
	 * 
	 * @return
	 */
	public static List<Banner> getBannerAd() {
		List<Banner> adList = new ArrayList<Banner>();

		Banner adDomain = new Banner();
		adDomain.setId("108078");
		adDomain.setTitle("Ӱ������ ");
		adDomain.setTopic("����������ĵ�Ӱ�����Ե����� �߿����ѧϰ��һ����Ϊ�������");
		// String path1 = "file:///android_asset/cirmovie.png";
		// adDomain.setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=bb99d6add2c8a786be2a4c0f5708c9c7/d50735fae6cd7b8900d74cd40c2442a7d9330e29.jpg");
		adDomain.setImgUrl(R.drawable.banner_movie);
		adDomain.setAd(false);
		adList.add(adDomain);

		Banner adDomain2 = new Banner();
		adDomain2.setId("108078");
		adDomain2.setTitle("���������ϰ");
		adDomain2.setTopic("�ٳ����С©��������Ϊ��׼����!");
		// adDomain2.setImgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=7cbcd7da78f40ad115e4c1e2672e1151/eaf81a4c510fd9f9a1edb58b262dd42a2934a45e.jpg");
		// String path2 = "file:///android_asset/speaking.png";
		adDomain2.setImgUrl(R.drawable.banner_speak);
		adDomain2.setAd(false);
		adList.add(adDomain2);

		Banner adDomain3 = new Banner();
		adDomain3.setId("108078");
		adDomain3.setTitle("�ݽ���ɢ����ϰ ");
		adDomain3.setTopic("������������װ��˫������ �����쵽������");
		// String path3 = "file:///android_asset/tone.png";
		adDomain3.setImgUrl(R.drawable.banner_tone);
		// adDomain3.setImgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=392ce7f779899e51788e3c1572a6d990/8718367adab44aed22a58aeeb11c8701a08bfbd4.jpg");
		adDomain3.setAd(false);
		adList.add(adDomain3);
		return adList;
	}

	public void myGetIntent() {
		// ��ȡ��һ��activity������ֵ
		Bundle bundle1 = getIntent().getExtras();
		int username = bundle1.getInt("username");
		String password = bundle1.getString("password");
	}

	public void mySendIntent(int key) {
		Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
		// newһ��Bundle���󣬲���Ҫ���ݵ����ݴ���
		 Bundle bundle = new Bundle();
		 bundle.putInt("key", key);
		// bundle.putString("password", password);
		// //��bundle����assign��Intent
		 intent.putExtras(bundle);
		// ������ת
		startActivity(intent);
	}

}