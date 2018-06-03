package com.System.UI;

import java.util.List;

import com.System.Utils.Logg;

import BO.BO;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class kEditText extends EditText implements TextWatcher, IControl
{
	  private Drawable dRight;
	  private Rect rBounds;	
	
	kIEditText myListener = null;
	
	public String Name = "";
	public String Val = "";
	
	private int IDField = 0;
	private int TextField = 0;
	private int LabelField = 0;
	public List<BO> lsBO = null;

	public kEditText(Context context)
	{
		super(context);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIEditText)context;
				addTextChangedListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kEditText, falta implementar interface kIEditText?? en " + context.getClass().getName());
				//throw ex;
			}
		}
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}
	
	  @Override
	  public void setCompoundDrawables(Drawable left, Drawable top,
	      Drawable right, Drawable bottom)
	  {
	    if(right !=null)
	    {
	      dRight = right;
	    }
	    super.setCompoundDrawables(left, top, right, bottom);
	  }
	  
	  @Override
	  public boolean onTouchEvent(MotionEvent event)
	  {

	    if(event.getAction() == MotionEvent.ACTION_UP && dRight!=null)
	    {
	      rBounds = dRight.getBounds();
	      final int x = (int)event.getX();
	      final int y = (int)event.getY();
	      //System.out.println("x:/y: "+x+"/"+y);
	      //System.out.println("bounds: "+bounds.left+"/"+bounds.right+"/"+bounds.top+"/"+bounds.bottom);
	      //check to make sure the touch event was within the bounds of the drawable
	      if(x>=(this.getRight()-rBounds.width()) && x<=(this.getRight()-this.getPaddingRight())
	          && y>=this.getPaddingTop() && y<=(this.getHeight()-this.getPaddingBottom()))
	      {
	        //System.out.println("touch");
	        this.setText("");
	        event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
	      }
	    }
	    return super.onTouchEvent(event);
	  }
	  
	  @Override
	  protected void finalize() throws Throwable
	  {
	    dRight = null;
	    rBounds = null;
	    super.finalize();
	  }

	public kEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIEditText)context;
				addTextChangedListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kEditText, falta implementar interface kIEditText?? en " + context.getClass().getName());
				//throw ex;
			}
		}
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}
	 
	public kEditText(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIEditText)context;
				addTextChangedListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kEditText, falta implementar interface kIEditText?? en " + context.getClass().getName());
				//throw ex;
			}
		}
		
		this.IDField = 0;
		this.TextField = 0;
		this.LabelField = 0;
	}
		
    @Override
    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) 
    {
		if(this.myListener != null)
		{
			this.myListener.onTextChangedEditText(this, cs, arg1, arg2, arg3);
		}
		else
		{
			Logg.error("Error al convertir KEditText_onTextChangedEditText, falta implementar interface kIEditText?? en " + this.getClass().getName());
		}
    }

	@Override
	public void afterTextChanged(Editable s) 
	{
		if(this.myListener != null)
		{
			this.myListener.afterTextChangedEditText(this, s);
		}
		else
		{
			Logg.error("Error al convertir KEditText_onTextChangedEditText, falta implementar interface kIEditText?? en " + this.getClass().getName());
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		if(this.myListener != null)
		{
			this.myListener.beforeTextChangedEditText(this, s, start, count, after);
		}
		else
		{
			Logg.error("Error al convertir KEditText_onTextChangedEditText, falta implementar interface kIEditText?? en " + this.getClass().getName());
		}
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
	public void DataBind() {
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addItem(String Elemento) {
		setText(Elemento);
	}

	@Override
	public void addItem(String Texto, String ID) {
		setText(Texto);
	}

	@Override
	public String getSelectedID() {
		return this.getText().toString();
	}

	@Override
	public String getSelectedText() {
		return this.getText().toString();
	}

	@Override
	public String getTypeControl() {
		return "kEditText";
	}

	@Override
	public void setSelectedText(String Texto) {
		setText(Texto);
	}

	@Override
	public String getLabelText() {
		return getHint().toString();
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
		this.setHint(Texto);
	}

	@Override
	public int getSelectedIndexs() {
		// TODO Auto-generated method stub
		return 0;
	}

}
