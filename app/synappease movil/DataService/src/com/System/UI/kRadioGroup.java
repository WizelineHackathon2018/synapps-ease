package com.System.UI;

import java.util.List;

import com.System.Utils.Logg;

import BO.BO;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class kRadioGroup extends RadioGroup implements IControl
{
	private int IDField;
	private int TextField;
	private int LabelField;

	public kRadioGroup(Context context)
	{
		super(context);
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}

	public kRadioGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}

	@Override
	public void setIDField(int IDField) {
		this.IDField = IDField;
	}

	@Override
	public int getIDField() {
		return this.IDField;
	}

	@Override
	public void setTextField(int TextField) {
		this.TextField = TextField;
	}

	@Override
	public int getTextField() {
		return this.TextField;
	}

	@Override
	public void setLabelField(int LabelField) {
		this.LabelField = LabelField;
	}

	@Override
	public int getLabelField() {
		return this.LabelField;
	}

	@Override
	public void setSelectedText(String Texto) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getSelectedText()
	{
		if(this.getCheckedRadioButtonId()!=-1)
		{
		    View radioButton = this.findViewById(this.getCheckedRadioButtonId());

		    RadioButton btn = (RadioButton) this.getChildAt(this.indexOfChild(radioButton));

		    return btn.getText().toString();
		}
		else
			return "";
	}

	@Override
	public void setLabel(String Texto) {
		// TODO Auto-generated method stub
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
	public void addItem(String Elemento) 
	{
		RadioButton rb = new RadioButton(this.getContext());
		rb.setText(Elemento);
		this.addView(rb);
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
		return this.indexOfChild(findViewById(this.getCheckedRadioButtonId()));
	}

}
