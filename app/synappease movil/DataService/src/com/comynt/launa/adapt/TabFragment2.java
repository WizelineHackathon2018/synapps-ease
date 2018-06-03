package com.comynt.launa.adapt;

import com.comynt.launa.Repositorio;
import com.comynt.launa.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TabFragment2 extends Fragment 
{
	Repositorio repositorio = Repositorio.getInstance();
	
	ListView lslmInventarioVenta = null;
	static adaptVerDatos lmadapterInventario = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View vi = inflater.inflate(R.layout.lmdashboardmetas, container, false);
		
		if(repositorio.lsVenta2 != null)
		{
			lslmInventarioVenta = (ListView)vi.findViewById(R.id.lsDatosCliente);

			lmadapterInventario = new adaptVerDatos(this, repositorio.lsVenta2, 1);
			lslmInventarioVenta.setAdapter(lmadapterInventario);

			lslmInventarioVenta.setClickable(false);
		}
		
		return vi;
	}
}
