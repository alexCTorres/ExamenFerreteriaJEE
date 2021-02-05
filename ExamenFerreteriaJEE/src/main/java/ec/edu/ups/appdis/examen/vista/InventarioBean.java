package ec.edu.ups.appdis.examen.vista;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import ec.edu.ups.appdis.examen.modelo.Inventario;
import ec.edu.ups.appdis.examen.modelo.Pedido;
import ec.edu.ups.appdis.examen.modelo.Productos;
import ec.edu.ups.appdis.examen.negocio.GestionInventarioON;
import ec.edu.ups.appdis.examen.negocio.GestionProductoOnRemoto;

@Named
@RequestScoped
public class InventarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Inventario newInventario;
	
	private List<Inventario> listaInventario;
	
	private GestionProductoOnRemoto remotoProd;
	
	private Pedido newPedido;
	
	private  double cantidadSolicitada;
	
	@Inject
	private GestionInventarioON inventarioON;

	public void instanciarObjetoNegocio() throws Exception {
		try {
			final Hashtable<String, Comparable> jndiProperties = new Hashtable<String, Comparable>();
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProperties.put("jboss.naming.client.ejb.context", true);

			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			jndiProperties.put(Context.SECURITY_PRINCIPAL, "ejb");
			jndiProperties.put(Context.SECURITY_CREDENTIALS, "ejb01");

			final Context context = new InitialContext(jndiProperties);

			final String lookupName = "ejb:/ExamenFerreteriaJEE/GestionProductoON!ec.edu.ups.appdis.examen.negocio.GestionProductoOnRemoto";

			this.remotoProd = (GestionProductoOnRemoto) context.lookup(lookupName);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public Inventario getNewInventario() {
		return newInventario;
	}

	public void setNewInventario(Inventario newInventario) {
		this.newInventario = newInventario;
	}

	public List<Inventario> getListaInventario() {
		return listaInventario;
	}

	public void setListaInventario(List<Inventario> listaInventario) {
		this.listaInventario = listaInventario;
	}
	
	public double getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	public void setCantidadSolicitada(double cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	public Pedido getNewPedido() {
		return newPedido;
	}

	public void setNewPedido(Pedido newPedido) {
		this.newPedido = newPedido;
	}

	@PostConstruct
	public void init() throws Exception {
		newInventario = new Inventario();
		newPedido = new Pedido();
		instanciarObjetoNegocio();
		listarInventario();
	}
	
	public void listarInventario() {
		listaInventario = inventarioON.listaInventario();

	}
	
	public String registrarPedido(String cod) {
		return "solicitarPedido";
	}
	
	public void solicitarPedido() {
		Productos prod = new Productos();
		double stockNuevo;
		try {
		prod = remotoProd.buscarProducto(newInventario.getProductos().getCodigo());
		double stockAc = prod.getStock();
		if(stockAc <= newPedido.getCantidad() ) {
			stockNuevo = stockAc - newPedido.getCantidad();
			prod.setStock(stockNuevo);
			remotoProd.actualizarProducto(prod);
		}else {
				System.out.println("cantodad solicitada mayor que el stock actual del proveedor");
			}
		
		}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


