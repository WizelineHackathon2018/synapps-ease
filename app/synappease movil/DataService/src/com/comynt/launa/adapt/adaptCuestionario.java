package com.comynt.launa.adapt;

import java.util.ArrayList;
import java.util.List;

import com.System.UI.ControlManager;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.R;

import BO.BO;
import BO.BOCheckVehiculo;
import BO.BOCuestionarioCliente;
import BO.BOInformacionCliente;
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

//adaptador para los cuestionarios dinamicos
public class adaptCuestionario extends BaseAdapter
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();
    
    public List<BO> lsInformacionCliente = new ArrayList<BO>();
    
    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;
    
    public ControlManager UImanager = null;
    private int tipo = -1;

    public adaptCuestionario(Activity a, List<BO> d, int tipo)
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
        this.tipo = tipo;
    }
    
    public adaptCuestionario(Activity a, List<BO> d, int tipo, List<BO> ls)
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
        
        if(ls != null)
        {
            for(int x = 0; x < ls.size(); x++)
            {
            	this.lsInformacionCliente.add(ls.get(x));
            }
        }
        
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UImanager = new ControlManager();
        this.tipo = tipo;
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
    	if(tipo == 0)  //Cuestionario Cliente, cargamos ademas las respuestas ya obtenidas
    	{
    		BOCuestionarioCliente CuestionarioCliente = null;
    		CuestionarioCliente = (BOCuestionarioCliente)data.get(position);

    		Logg.info("GENERAR ITEM ACTIVIDAD " + position + CuestionarioCliente.toString());
            vi = convertView;

        	if(CuestionarioCliente.Tipo == 0)
        	{
                if( position >= UImanager.size() )
                {
                	vi = inflater.inflate(R.layout.itemcuelsrespuestaunica, null);
            		UImanager.AgregarRadioGroup(vi, R.id.lblItemRespuestaUnica, R.id.rbItemRespuestaUnica, 
            				(position + 1) + "-" + data.size() + ")" + CuestionarioCliente.Pregunta, Utils.Split(CuestionarioCliente.Respuesta, "/"));
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}
        	else if(CuestionarioCliente.Tipo == 1)
        	{
                if( position >= UImanager.size() )
                {
                	vi = inflater.inflate(R.layout.itemcuelsrespuestaunica, null);
                	UImanager.AgregarRadioGroup(vi, R.id.lblItemRespuestaUnica, R.id.rbItemRespuestaUnica, 
                			(position + 1) + "-" + data.size() + ")" + CuestionarioCliente.Pregunta, Utils.Split(CuestionarioCliente.Respuesta, "/"));
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}
        	else if(CuestionarioCliente.Tipo == 2)
        	{
                if( position >= UImanager.size() )
                {
                	
//                	vi = inflater.inflate(R.layout.itemcuelsrespuestalibre, null);
//                	UImanager.AgregarTextBox(vi, R.id.lblItemRespuestaLibre, R.id.txtItemRespuestaLibre, 
//                			(position + 1) + "-" + data.size() + ")" + CuestionarioCliente.Pregunta, CuestionarioCliente.Respuesta);
                	
                	vi = inflater.inflate(R.layout.itemcuelsrespuestalibre, null);
                	UImanager.AgregarTextBox(vi, R.id.lblItemRespuestaLibre, R.id.txtItemRespuestaLibre, 
                			(position + 1) + "-" + data.size() + ")" + CuestionarioCliente.Pregunta, RevisarRespuestaCliente(CuestionarioCliente));
                	
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}

            return vi;
    	}
    	else if(tipo == 1)  //Checklist
    	{
    		BOCheckVehiculo CheckVehiculo = null;
    		CheckVehiculo = (BOCheckVehiculo)data.get(position);

    		Logg.info("GENERAR ITEM ACTIVIDAD " + position + CheckVehiculo.toString());

            vi = convertView;
    		
        	if(CheckVehiculo.Tipo == 0)
        	{
                if( position >= UImanager.size() )
                {
                	vi = inflater.inflate(R.layout.itemcuelsrespuestaunica, null);
            		UImanager.AgregarRadioGroup(vi, R.id.lblItemRespuestaUnica, R.id.rbItemRespuestaUnica, 
            				(position + 1) + "-" + data.size() + ")" + CheckVehiculo.Texto, Utils.Split(CheckVehiculo.Respuesta, "/"));
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}
        	else if(CheckVehiculo.Tipo == 1)
        	{
                if( position >= UImanager.size() )
                {
                	vi = inflater.inflate(R.layout.itemcuelsrespuestaunica, null);
                	UImanager.AgregarRadioGroup(vi, R.id.lblItemRespuestaUnica, R.id.rbItemRespuestaUnica, 
                			(position + 1) + "-" + data.size() + ")" + CheckVehiculo.Texto, Utils.Split(CheckVehiculo.Respuesta, "/"));
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}
        	else if(CheckVehiculo.Tipo == 2)
        	{
                if( position >= UImanager.size() )
                {
                	vi = inflater.inflate(R.layout.itemcuelsrespuestalibre, null);
                	UImanager.AgregarTextBox(vi, R.id.lblItemRespuestaLibre, R.id.txtItemRespuestaLibre, 
                			(position + 1) + "-" + data.size() + ")" + CheckVehiculo.Texto, CheckVehiculo.Respuesta);
                }
                else
                {
                	vi = UImanager.getView(position);
                }
        	}

            return vi;
    	}
    	else
    	{
        	vi = inflater.inflate(R.layout.itemlsactividades, null);
            return vi;
    	}
    }
    
    //Revisamos las respuestas contra las preguntas a ver si existe la respuesta, si existe se regresa, si no solo vacio
    private String RevisarRespuestaCliente(BOCuestionarioCliente CuestionarioCliente)
    {
    	if (lsInformacionCliente != null)
    	{
    		for (int x = 0; x < lsInformacionCliente.size(); x++)
    		{
    			BOInformacionCliente InformacionCliente = (BOInformacionCliente) lsInformacionCliente.get(x);
    			
    			if (CuestionarioCliente.IdCuestionarioCliente == InformacionCliente.IdCuestionarioCliente)
    			{
    				return InformacionCliente.Respuesta;
    			}
    		}
    	}
    	
    	return CuestionarioCliente.Respuesta;
    }

}
