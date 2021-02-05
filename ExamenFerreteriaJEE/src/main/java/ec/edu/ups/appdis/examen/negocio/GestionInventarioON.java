package ec.edu.ups.appdis.examen.negocio;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.appdis.examen.dao.InventarioDAO;
import ec.edu.ups.appdis.examen.modelo.Inventario;

@Stateless
public class GestionInventarioON {

	@Inject
	private InventarioDAO daoInventario;
	
	public List<Inventario> listaInventario(){
		return daoInventario.listaInventario();
	}
	
	public boolean insert(Inventario inv) {
		try {
			daoInventario.insertJPA(inv);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean actualizar(Inventario inv) {
		try {
			daoInventario.updateJPA(inv);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
