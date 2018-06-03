package com.comynt.launa.adapt;

import BO.*;

import com.comynt.launa.Repositorio;
import com.comynt.launa.frm.frmActividades;
import com.comynt.launa.R;

import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class adaptResumenVenta extends BaseAdapter
{
	public Repositorio repositorio = Repositorio.getInstance();
	
    public List<BONotaDetalle> data = new ArrayList<BONotaDetalle>();
    
    public boolean Recarga = false;
    
    public ArrayList<HashMap<String, String>> datosListaHash = new ArrayList<HashMap<String, String>>();
    
    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;
    private Filter myFilter;
    private boolean cambio = true;

    public adaptResumenVenta(Activity a)
    {
        activity = a;
        
        for(int x = 0; x < repositorio.productos.size(); x++)
        {
        	data.add((BONotaDetalle) repositorio.productos.get(x));
        }
        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	BONotaDetalle AlmacenMovil = null;
    	AlmacenMovil = (BONotaDetalle)data.get(position);
    	
        vi = inflater.inflate(R.layout.itemlsverdato, null);

        TextView lblfechaagenda =(TextView)vi.findViewById(R.id.lblArribaDato);
        lblfechaagenda.setText(AlmacenMovil.Nombre);

        TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
        
        txtItemHora.setText(AlmacenMovil.Cantidad + 
        		" " + AlmacenMovil.UnidadMedida + 
        		" x $" + AlmacenMovil.Precio + 
        		" = $" + Utils.toMoneyFormat(AlmacenMovil.Cantidad * AlmacenMovil.Precio));

        return vi;
    }
    
}
