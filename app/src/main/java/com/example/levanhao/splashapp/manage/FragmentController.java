package com.example.levanhao.splashapp.manage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by LeVanHao on 10/15/2017.
 */

public class FragmentController {

	public static void addFragment(FragmentManager manager, Fragment fragment, int id) {
		final FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment).commit();
	}


}
