package com.System.UI;

import java.util.List;
import java.util.Vector;

import BO.BO;

public class ChildControlBOManager implements IControl
{
	private Vector lsControles = null;

	public ChildControlBOManager()
	{
		/* */     this.lsControles = new Vector();
	}

	public ChildControlBOManager(int size)
	{
		/* */     this.lsControles = new Vector(size);
	}

	public void addControl(ChildControlBOItem Item)
	{
		/* */     this.lsControles.addElement(Item);
	}

	public void setControl(ChildControlBOItem Item, int indice)
	{
		/* */     this.lsControles.setElementAt(Item, indice);
	}

	public ChildControlBOItem getControl(int indice)
	{
		/* */     return (ChildControlBOItem)this.lsControles.elementAt(indice);
	}

	public int size()
	{
		/* */     return this.lsControles.size();
	}

	public void setIDField(int IDField)
	{
	}

	public int getIDField()
	{
		/* */     return 0;
	}

	public void setTextField(int TextField)
	{
	}

	public int getTextField()
	{
		/* */     return 0;
	}

	public void setLabelField(int LabelField)
	{
	}

	public int getLabelField()
	{
		/* */     return 0;
	}

	public void DataBind()
	{
	}

	public void Clear()
	{
	}

	public void addItem(String Elemento)
	{
	}

	public void addItem(String Texto, String ID)
	{
	}

	public String getSelectedID()
	{
		/* */     return "";
	}

	public String getSelectedText()
	{
		return "";
	}

	public String getTypeControl()
	{
		return "ChildControlContainer";
	}

	public void setSelectedText(String Texto)
	{
	}

	public String getLabelText()
	{
		return "";
	}

	public void Dispose()
	{
	}

	@Override
	public void setBO(List<BO> lsBO) {
		// TODO Auto-generated method stub

	}
	@Override
	public BO getBO(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLabel(String Texto) {
		// TODO Auto-generated method stub

	}
	@Override
	public int getSelectedIndexs() {
		// TODO Auto-generated method stub
		return 0;
	}
}
