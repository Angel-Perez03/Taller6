package model;

import java.util.ArrayList;

public class Combo implements Producto
{
	private float descuento;
	private String nombreCombo;
	private ArrayList<Producto> items;
	private int cal;

	public Combo(String nombre, float descuento)
	{
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.items = new ArrayList<Producto>();

	}

	public void agregarItemACombo(Producto itemCombo)
	{
		items.add(itemCombo);
		cal += itemCombo.getCalorias();
	}

	public ArrayList<Producto> getItems()
	{

		return this.items;
	}

	@Override
	public float getPrecio()
	{

		return this.descuento;
	}

	@Override
	public String getNombre()
	{

		return this.nombreCombo;
	}

	@Override
	public String generarTextoFactura()
	{

		return null;
	}

	@Override
	public int getCalorias()
	{
		return this.cal;
	}

}
