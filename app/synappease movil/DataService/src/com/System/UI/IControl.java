package com.System.UI;

import java.util.List;

import BO.BO;

public abstract interface IControl
{
  public abstract void setIDField(int IDField);

  public abstract int getIDField();
  

  public abstract void setTextField(int TextField);

  public abstract int getTextField();

  
  public abstract void setLabelField(int LabelField);

  public abstract int getLabelField();
  
  public abstract int getSelectedIndexs();

  
  public abstract void setSelectedText(String Texto);
  
  public abstract String getSelectedText();
  
  
  public abstract void setLabel(String Texto);

  public abstract String getLabelText();
  

  public abstract void setBO(List<BO> lsBO);
  
  public abstract BO getBO(int index);
  
  
  public abstract void addItem(String Elemento);

  public abstract void addItem(String Texto, String ID);

  public abstract String getTypeControl();
  
  public abstract String getSelectedID();
  
  public abstract void DataBind();

  public abstract void Clear();
  
  public abstract void Dispose();
}
