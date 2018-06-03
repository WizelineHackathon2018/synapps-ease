package com.System.UI;

import java.util.List;

import BO.BO;

public class ControlItemNode implements IControl
{
	public IControl Izquierda = null;
	public IControl Derecha = null;

	public ControlItemNode()
	{
	}
	
	public ControlItemNode(IControl Izquierda, IControl Derecha)
	{
		this.Izquierda = Izquierda;
		this.Derecha = Derecha;
	}

	public void SetNode(IControl Izquierda, IControl Derecha)
	{
		this.Izquierda = Izquierda;
		this.Derecha = Derecha;
	}
	
	public void setIzquierda(IControl Izquierda)
	{
		this.Izquierda = Izquierda;
	}
	
	public void setDerecha(IControl Derecha)
	{
		this.Derecha = Derecha;
	}

	@Override
	public void setIDField(int IDField) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getIDField() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTextField(int TextField) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getTextField() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLabelField(int LabelField) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getLabelField() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void DataBind() {
		// TODO Auto-generated method stub
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addItem(String Elemento) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addItem(String Texto, String ID) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getSelectedID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSelectedText() {
		return this.Derecha.getSelectedText();
	}

	@Override
	public String getTypeControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedText(String Texto) 
	{
		this.Derecha.setSelectedText(Texto);
	}

	@Override
	public String getLabelText() {
		return this.Izquierda.getLabelText();
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
	public void Dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setLabel(String Texto) {
		this.Izquierda.setLabel(Texto);
	}

	@Override
	public int getSelectedIndexs() {
		return this.Derecha.getSelectedIndexs();
	}

}
