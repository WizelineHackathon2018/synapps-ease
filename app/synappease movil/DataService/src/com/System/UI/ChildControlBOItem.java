package com.System.UI;

import BO.BO;

public class ChildControlBOItem
{
	public String Name;
	public BO bo;
	public IControl control;

	public ChildControlBOItem()
	{
	}
 
	public ChildControlBOItem(BO bo, IControl control)
	{
		this.bo = bo;
		this.control = control;
	}
}
