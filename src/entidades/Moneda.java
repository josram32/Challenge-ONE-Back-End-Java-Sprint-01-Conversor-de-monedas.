package entidades;

public class Moneda {
	
	private String nombre;
	private String simbolo;
	private double tipoCambio;	
	
	public Moneda(String nombre,String simbolo, double tipoCambio) {
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.tipoCambio = tipoCambio;
	}
	
	
	
	public String getSimbolo() {
		return simbolo;
	}


	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

}
