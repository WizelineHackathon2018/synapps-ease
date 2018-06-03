package com.comynt.launa.adapt;

import com.comynt.launa.Repositorio;
import com.comynt.launa.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TabFragment1 extends Fragment 
{
	Repositorio repositorio = Repositorio.getInstance();
	
	ListView lslmInventarioVenta = null;
	static adaptVerDatos lmadapterInventario = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View vi = inflater.inflate(R.layout.lmdashboardinventario, container, false);
		
		if(repositorio.lsAlmacenMovil2 != null)
		{
			lslmInventarioVenta = (ListView)vi.findViewById(R.id.lsDatosCliente);

			lmadapterInventario = new adaptVerDatos(this, repositorio.lsAlmacenMovil2, 0);
			lslmInventarioVenta.setAdapter(lmadapterInventario);

			lslmInventarioVenta.setClickable(false);
		}
		
		return vi;
	}
}
