package com.System.UI;

import android.text.Editable;

public abstract interface kIEditText 
{
    public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1, int arg2, int arg3);
	public void afterTextChangedEditText(kEditText e, Editable s);
	public void beforeTextChangedEditText(kEditText e, CharSequence s, int start, int count, int after);
}
