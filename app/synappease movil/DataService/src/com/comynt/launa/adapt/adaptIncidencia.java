package com.comynt.launa.adapt;

import java.util.ArrayList;
import java.util.List;

import com.System.UI.ControlManager;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import com.comynt.launa.R;

import BO.*;
import BO.BOItinerario;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

public class adaptIncidencia extends BaseAdapter
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();

    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;

    public ControlManager UImanager = null;

    public adaptIncidencia(Activity a, List<BO> d)
    {
        activity = a;
        
        if(d != null)
        {
            for(int x = 0; x < d.size(); x++)
            {
            	data.add(d.get(x));
            	dataoriginal.add(d.get(x));
            }
        }
        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UImanager = new ControlManager();
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
    
    public String getText()
    {
		for (int x = 0; x < UImanager.size(); x++)
		{
			if(!UImanager.getSelectedText(x).equals(""))
			{
				return UImanager.getSelectedText(x);
			}
		}
    	
    	return "";
    }
    
    public void addBO(BO item)
    {
    	dataoriginal.add(item);
    	data.clear();
        for(int x = 0; x < dataoriginal.size(); x++)
        {
        	data.add(dataoriginal.get(x));
        }
        notifyDataSetInvalidated();
    }
    
    public void editBO(BO item, int index)
    {
    	dataoriginal.set(index, item);
    	data.clear();

        for(int x = 0; x < dataoriginal.size(); x++)
        {
        	data.add(dataoriginal.get(x));
        }

        notifyDataSetInvalidated();
    }
 
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	BOCuestionarioCliente Precuestionario = null;
		Precuestionario = (BOCuestionarioCliente)data.get(position);

		Logg.info("GENERAR Precuestionario ACTIVIDAD " + position);

        vi = convertView;
		
        //if( position >= UImanager.size() )
        if( position >= 0 )
        {
        	vi = inflater.inflate(R.layout.itemcuelsrespuestaunica, null);
    		UImanager.AgregarRadioGroup(vi, R.id.lblItemRespuestaUnica, R.id.rbItemRespuestaUnica, 
    				(position + 1) + "-" + data.size() + ")" + Precuestionario.Pregunta, Utils.Split(Precuestionario.Respuesta, "/"));
        }
        else
        {
        	vi = UImanager.getView(position);
        }

        return vi;
    }
    
}
