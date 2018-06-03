package com.System.Showcase;

public class showcaseItem 
{
	public int Tipo = 0; //0 para view y 1 para no view
	public int IdView = 0;
	public String Text = "";
	
	public showcaseItem(int Tipo, int IdView)
	{
		this.Tipo = Tipo;
		this.IdView = IdView;
	}
	
	public showcaseItem(int Tipo, String Text)
	{
		this.Tipo = Tipo;
		this.Text = Text;
	}
}
