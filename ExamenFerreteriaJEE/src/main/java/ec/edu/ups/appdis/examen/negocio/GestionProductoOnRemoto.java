package ec.edu.ups.appdis.examen.negocio;



import ec.edu.ups.appdis.examen.modelo.Productos;

public interface GestionProductoOnRemoto {
	
	public Productos buscarProducto(String cod);
	public boolean actualizarProducto(Productos prod) throws Exception;

}
