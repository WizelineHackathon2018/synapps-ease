package com.System.RMS;

import java.util.Comparator;

import BO.BO;

public class rmsComparator implements Comparator<BO> 
{
	int IDField = 0;
	
	public rmsComparator(int IDField)
	{
		this.IDField = IDField;
	}
	
	@Override
	public int compare(BO lhs, BO rhs) 
	{
		rmsInterface rmslhs = (rmsInterface) lhs;
		rmsInterface rmsrhs = (rmsInterface) rhs;
		
		String s1 = rmslhs.getFieldValue(IDField);
		String s2 = rmsrhs.getFieldValue(IDField);

		if (s1.compareTo(s2) > 0)
		{
			return 1;
		}
		if (s1.compareTo(s2) == 0) 
		{
			return 0;
		}
		return -1;
	}
}
