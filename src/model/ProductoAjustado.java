package model;

public class ProductoAjustado implements Producto
{
	private String nombreNuevo;
	private float precioNuevo;
	private int calorias;

	public ProductoAjustado(String nombre, float precio_nuevo, int calorias)
	{

		this.nombreNuevo = nombre;
		this.precioNuevo = precio_nuevo;
		this.calorias = calorias;
	}

	@Override
	public float getPrecio()
	{
		return this.precioNuevo;
	}

	@Override
	public String getNombre()
	{
		return this.nombreNuevo;
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