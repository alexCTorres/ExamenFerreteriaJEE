package ec.edu.ups.appdis.examen.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ec.edu.ups.appdis.examen.modelo.Inventario;
import ec.edu.ups.appdis.examen.modelo.Productos;

@Stateless
public class InventarioDAO {

	@Inject
	private EntityManager em;

	// metodo para listar inventario
	public List<Inventario> listaInventario() {
		String jpql = "Select i FROM Inventario i";
		Query q = em.createQuery(jpql, Inventario.class);
		return (List<Inventario>) q.getResultList();
	}

	// insertar productos al inventario
	public boolean insertJPA(Inventario inv) throws SQLException {
		em.persist(inv);
		return true;
	}

	// metodo de update con JPA utilizando el Entity manager
	public boolean updateJPA(Inventario inv) throws SQLException {
		em.merge(inv);
		return true;
	}
}
