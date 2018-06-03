package com.comynt.launa.adapt;

import com.System.Utils.Logg;
import com.comynt.launa.R;
import com.infragistics.controls.charts.PieChartView;
import com.infragistics.controls.gauges.*;
import com.infragistics.graphics.SolidColorBrush;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

//GAUJE DE VISITA
public class TabFragment4 extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
    	RelativeLayout rootView = (RelativeLayout)inflater.inflate(R.layout.fragment_main, container, false);
        
    	try
    	{
        	RadialGaugeView gauge = new RadialGaugeView(rootView.getContext());
        	gauge.setTransitionDuration(1500);
        	gauge.setInterval(10);
        	
        	//Valor de la aguja
        	gauge.setValue(60);
        	
        	gauge.setMaximumValue(100);
        	gauge.setMinimumValue(0);
        	
        	//Rojo de 0 a 70
    		gauge.addRange(getRango(0, 70, "#FF0000"));
    		
        	//Naranja de 70 a 80
    		gauge.addRange(getRango(70, 80, "#FF6600"));
    		
        	//Amarillo de 80 a 90
    		gauge.addRange(getRango(80, 90, "#FFFF00"));

        	//Verde de 90 a 100
    		gauge.addRange(getRango(90, 100, "#81B632"));
    		
    		rootView.removeAllViews();
    		rootView.addView(gauge);
    	}
    	catch(Exception ex)
    	{
    		Logg.info(ex.getMessage());
    	}
        
        return rootView;
	}
	
	private RadialGaugeRange getRango(int inicio, int fin, String ColorExa)
	{
    	RadialGaugeRange range1 = new RadialGaugeRange();
		range1.setStartValue(inicio);
		range1.setEndValue(fin) ;
		SolidColorBrush brush = new SolidColorBrush() ;
		brush.setColor(Color.parseColor(ColorExa));
			
		range1.setBrush(brush);
		
		if(inicio == 0)
		{
			range1.setOuterStartExtent(0.45);
		}
		else
		{
			range1.setOuterStartExtent(0.40);	
		}
		
		range1.setOuterEndExtent(0.40);
		
		return range1;
	}
}
