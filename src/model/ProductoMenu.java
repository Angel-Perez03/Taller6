package model;

public class ProductoMenu implements Producto
{
	private String nombre="";
	private float precioBase;
	private int calorias;

	public ProductoMenu(String nombre, int precioBase, int calorias)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	@Override
	public float getPrecio()
	{
		return this.precioBase;
	}

	@Override
	public String getNombre()
	{
		return this.nombre;
	}

	@Override
	public String generarTextoFactura()
	{
		return null;
	}

	@Override
	public int getCalorias()
	{
		return this.calorias;
	}

}