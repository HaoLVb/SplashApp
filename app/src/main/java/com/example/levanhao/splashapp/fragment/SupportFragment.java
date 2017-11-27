package com.example.levanhao.splashapp.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.levanhao.splashapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements View.OnClickListener {


	public SupportFragment() {
		// Required empty public constructor
	}

	public static SupportFragment newInstance() {
		SupportFragment fragment = new SupportFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_support, container, false);
		this.callLayout = (RelativeLayout) view.findViewById(R.id.callLayout);
		this.emailLayout = (RelativeLayout) view.findViewById(R.id.emailLayout);
		this.callLayout.setOnClickListener(this);
		this.emailLayout.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.callLayout:
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:1900636779"));

			if (ActivityCompat.checkSelfPermission(getContext(),
					Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			startActivity(callIntent);
			break;
		case R.id.emailLayout:
			sendEmail();
			break;
		}
	}

	protected void sendEmail() {
		String[] TO = {"hotro@moki.vn"};
		Intent emailIntent = new Intent(Intent.ACTION_SEND);

		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

		try {
			startActivity(Intent.createChooser(emailIntent, "Gửi mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
		}
	}

	private View view;
	private RelativeLayout callLayout;
	private RelativeLayout emailLayout;

}
