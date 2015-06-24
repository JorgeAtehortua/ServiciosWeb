/**
 * 
 */
package com.amhable.ws;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;







import com.amhable.dominio.CategoriaDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.CategoriaLN;

/**
 * Clase que contiene los servicios para la tabla Categoria
 * @author Luisa
 *
 */
@Path ("Categoria")
@Component
public class CategoriaWs {
	
	Logger log;
	/**
	 * Objeto tipo CategoriaDto con el que se manejan los datos de las categorias
	 */
	CategoriaDto categoria;
	/**
	 * Inyeccion de dependecias
	 */
	private CategoriaLN categoriaLN;

	/**
	 * @return the categoriaLN
	 */
	public CategoriaLN getCategoriaLN() {
		return categoriaLN;
	}

	/**
	 * @param categoriaLN the categoriaLN to set
	 */
	public void setCategoriaLN(CategoriaLN categoriaLN) {
		this.categoriaLN = categoriaLN;
	}
	
	/**
	 * Servicio que obtiene la informacion de una categoria segun el identificador
	 * 
	 * @param identificador de la categoria que va a obtener
	 * @return objeto tipo CategoriaDto con la informacion de la categoria
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerCategoria")
	public CategoriaDto obtenerCategoria(@QueryParam("idCategoria")Integer idCategoria) throws RemoteException, MyException{
		categoria=new CategoriaDto();
		log= Logger.getLogger(this.getClass());
		if (idCategoria == null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar identificador de la categoria");
		}
		try{
			categoria=categoriaLN.obtenerCategoria(idCategoria);
		}catch(MyException e){
			log.error("Error obteniendo categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (categoria == null) {
			throw new MyException("No existe una categoria con el identificador ingresado");
		}
		return categoria;
		
	}
	/**
	 * Servicio que obtiene la lista con todas las categorias de la base de datos
	 * @return lista con todas las categorias de la base de datos
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("obtenerCategorias")
	public List<CategoriaDto> obtenerCategorias() throws RemoteException, MyException{
		List<CategoriaDto> categorias=new ArrayList<CategoriaDto>();
		
		log= Logger.getLogger(this.getClass());
		
		try{
			categorias=categoriaLN.obtenerCategorias();
			
		}catch(MyException e){
			log.error("Error obteniendo la lista de categorias (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return categorias;
		
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("guardarCategoria")
	public String guardarCategoria(
			@QueryParam("idCategoria") Integer idCategoria,
			@QueryParam("nombre") String nombre ) throws RemoteException, MyException{
		categoria=new CategoriaDto();
		log= Logger.getLogger(this.getClass());
		if (idCategoria==null || "".equals(idCategoria)) {
			return("Debe ingresar identificador de la categoria");
		}
		if(nombre==null || "".equals(nombre)){
			return("Debe ingresar nombre de la categoria");
		}
		
		try{
			categoria=categoriaLN.obtenerCategoria(idCategoria);
			if(categoria!=null){
				return "Ya existe una categoria con ese identificador";
			}
			categoriaLN.guardar(idCategoria, nombre);
		}catch(MyException e){
			log.error("Error guardando la categoria (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		return "Categoria guardada exitosamente";		
	}
	

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("eliminarCategoria")
	public String eliminarCategoria(
			@QueryParam("idCategoria") Integer idCategoria)throws RemoteException, MyException{
		categoria=new CategoriaDto();
		log= Logger.getLogger(this.getClass());
		if (idCategoria==null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar identificador de la categoria");
		}
			
		try{
			categoria=categoriaLN.obtenerCategoria(idCategoria);
			if(categoria==null){
				return "No existe una categoria con ese identificador";
			}
			
		}catch(MyException e){
			log.error("Error obteniendo la categoria (WS): "+idCategoria, e);
			throw new RemoteException(e.getMessage());
		}
		
		try{
			categoriaLN.eliminar(idCategoria);
			
		}catch(MyException e){
			log.error("Error eliminando la categoria (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		
		return "Categoria eliminada exitosamente";		
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("actualizarCategoria")
	public String actualizarCategoria(
			@QueryParam("idCategoria")Integer idCategoria,
			@QueryParam("nombre")String nombre ) throws RemoteException, MyException{
		categoria=new CategoriaDto();
		log= Logger.getLogger(this.getClass());
		if (idCategoria==null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar identificador de la categoria");
		}
		if(nombre==null || "".equals(nombre)){
			throw new MyException("Debe ingresar nombre de la categoria");
		}
		
		try{
			categoria=categoriaLN.obtenerCategoria(idCategoria);
			if(categoria==null){
				return "La categoria a actualizar no existe";
			}
			
		}catch(MyException e){
			log.error("Error obteniendo la categoria (WS): "+idCategoria, e);
			throw new RemoteException(e.getMessage());
		}
		
		try{
			categoriaLN.actualizar(idCategoria, nombre);
			
		}catch(MyException e){
			log.error("Error actualizando la categoria (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		
		return "Categoria actualizada exitosamente";
		
	}
	
	

}
