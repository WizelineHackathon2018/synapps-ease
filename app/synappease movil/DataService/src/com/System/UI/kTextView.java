package com.System.UI;

import java.util.List;

import com.System.Utils.Logg;

import BO.BO;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class kTextView extends TextView implements IControl
{
	public String Name = "";
	public String Val = "";
	
	private int IDField = 0;
	private int TextField = 0;
	private int LabelField = 0;
	public List<BO> lsBO = null;

	public kTextView(Context context) 
	{
		super(context);
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}

	public kTextView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}

	public kTextView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
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
	public void setSelectedText(String Texto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return this.getText().toString();
	}

	@Override
	public void setLabel(String Texto) {
		// TODO Auto-generated method stub
		this.setText(Texto);
	}

	@Override
	public String getLabelText() {
		// TODO Auto-generated method stub
		return null;
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
	public void addItem(String Elemento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItem(String Texto, String ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTypeControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSelectedID() {
		// TODO Auto-generated method stub
		return null;
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
	public void Dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSelectedIndexs() {
		// TODO Auto-generated method stub
		return 0;
	}

}
