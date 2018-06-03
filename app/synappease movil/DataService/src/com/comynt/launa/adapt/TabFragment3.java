package com.comynt.launa.adapt;

import com.comynt.launa.R;
import com.comynt.launa.Repositorio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment3 extends Fragment 
{
	Repositorio repositorio = Repositorio.getInstance();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View vi = inflater.inflate(R.layout.lmdashboardopciones, container, false);
		
		TextView lbl2 =(TextView)vi.findViewById(R.id.lbl2);
		lbl2.setText("Versión " + repositorio.versionName + ".");		
		
		return vi;
	}
}
