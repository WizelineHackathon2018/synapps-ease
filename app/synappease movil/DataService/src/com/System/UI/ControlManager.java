package com.System.UI;

import java.util.Vector;
import com.comynt.launa.R;
import android.view.View;
import android.widget.TextView;

public class ControlManager
{
	private Vector controles;
	private Vector views;

	public ControlManager()
	{
		this.controles = new Vector();
		this.views = new Vector();
	}

	public int size()
	{
		return this.controles.size();
	}

	public void AgregarTextBox(View ViewParent, int IDLabelkTextView, int IDTextkEditText, String Label, String Text)
	{
		kTextView lblIzquierda =(kTextView)ViewParent.findViewById(IDLabelkTextView);
		lblIzquierda.setText(Label.toUpperCase());

		kEditText tztDerecha =(kEditText)ViewParent.findViewById(IDTextkEditText);
		tztDerecha.setHint(Text.toUpperCase());

		ControlItemNode nodo = new ControlItemNode(lblIzquierda, tztDerecha);

		this.controles.addElement(nodo);
		this.views.addElement(ViewParent);
	}

	public void AgregarRadioGroup(View ViewParent, int IDLabelkTextView, int IDTextkEditText, String Label, String[] DataSource)
	{
		kTextView lblIzquierda =(kTextView)ViewParent.findViewById(IDLabelkTextView);
		lblIzquierda.setText(Label.toUpperCase());

		kRadioGroup rgDerecha =(kRadioGroup)ViewParent.findViewById(IDTextkEditText);

		for (int x = 0; x < DataSource.length; x++)
		{
			rgDerecha.addItem(DataSource[x].toUpperCase());
		}

		//if (DataSource.length > 0)
		//	rgDerecha.get  //seleccionamos el primero

		ControlItemNode nodo = new ControlItemNode(lblIzquierda, rgDerecha);

		this.controles.addElement(nodo);
		this.views.addElement(ViewParent);
	}

	public void AgregarControl(IControl control)
	{
		this.controles.addElement(control);
	}

	public String getSelectedText(int ControlNumbrer)
	{
		if(this.controles.size() > ControlNumbrer)
		{
			IControl control = (IControl)this.controles.elementAt(ControlNumbrer);
			return control.getSelectedText();
		}
		else
		{
			return "";
		}
	}

	public String getSelectedID(int ControlNumbrer)
	{
		IControl control = (IControl)this.controles.elementAt(ControlNumbrer);

		return control.getSelectedID();
	}

	public int ControlsCount()
	{
		return this.controles.size();
	}

	public String getLabelControl(int ControlNumbrer)
	{
		IControl control = (IControl)this.controles.elementAt(ControlNumbrer);

		return control.getLabelText();
	}

	public void setSelectedText(String texto, int ControlNumbrer)
	{
		IControl control = (IControl)this.controles.elementAt(ControlNumbrer);

		control.setSelectedText(texto);
	}

	public IControl getControl(int ControlNumbrer)
	{
		return (IControl)this.controles.elementAt(ControlNumbrer);
	}

	public View getView(int ViewNumbrer)
	{
		return (View)this.views.elementAt(ViewNumbrer);
	}

	public void Remove(int ControlNumbrer)
	{
		this.controles.removeElementAt(ControlNumbrer);
	}

	public int getSelecteIndex(int index)
	{
		IControl control = (IControl)this.controles.elementAt(index);
		return control.getSelectedIndexs();
	}

}
