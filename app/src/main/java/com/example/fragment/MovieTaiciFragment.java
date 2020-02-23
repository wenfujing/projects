package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.itingshuo.MovieActivity;
import com.example.project1.R;


public class MovieTaiciFragment extends Fragment {
	private TextView taiciTextView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =View.inflate(getActivity(), R.layout.movie_taici_fragment, null);
		taiciTextView=(TextView) view.findViewById(R.id.taici);
		String taiciString =((MovieActivity)getActivity()).getTaici();
		String taiciString2=taiciString.replaceAll("##", "\n");
		taiciTextView.setText(taiciString2);
		return view;
	}
}