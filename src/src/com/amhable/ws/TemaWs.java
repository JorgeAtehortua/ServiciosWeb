package com.amhable.ws;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amhable.dominio.TemaDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.TemaLN;

/**
 * Clase que contiene los servicios para la tabla tema
 * 
 * @author Jorge Atehortua
 *
 */
@Path("Tema")
@Component
public class TemaWs {
	Logger log;
	/**
	 * Objeto tipo TemaDto con el que se manejan los datos de los temas
	 */
	TemaDto tema;
	/**
	 * Inyeccion de dependencias
	 */
	private TemaLN temaLN;

	/**
	 * @return the temaLN
	 */
	public TemaLN getTemaLN() {
		return temaLN;
	}

	/**
	 * @param temaLN
	 *            the temaLN to set
	 */
	public void setTemaLN(TemaLN temaLN) {
		this.temaLN = temaLN;
	}

	/**
	 * Servicio que obtiene la informacion de un tema segun el identificador
	 * 
	 * @param idTema
	 *            identificador del tema a obtener
	 * @return objeto tipo TemaDto con la informacion del tema obtenido
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("obtenerTema")
	public TemaDto obtenerTema(@QueryParam("idTema") Integer idTema)
			throws RemoteException, MyException {
		tema = new TemaDto();
		log = Logger.getLogger(this.getClass());
		if (idTema == null || "".equals(idTema)) {
			throw new MyException("Debe ingresar identificador del tema");
		}
		try {
			tema = temaLN.obtenerTema(idTema);
		} catch (MyException e) {
			log.error("Error obteniendo tema (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (tema == null) {
			throw new MyException(
					"No existe un tema con el identificador ingresado");
		}
		return tema;

	}

	/**
	 * Servicio que obtiene la lista con todos los temas de la base de datos
	 * 
	 * @return lista con todos los temas de la base de datos
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("obtenerTemas")
	public List<TemaDto> obtenerTemas() throws RemoteException, MyException {
		List<TemaDto> temas = new ArrayList<TemaDto>();
		log = Logger.getLogger(this.getClass());

		try {
			temas = temaLN.obtenerTemas();
		} catch (MyException e) {
			log.error("Error obteniendo la lista de temas (WS)", e);
			throw new RemoteException(e.getMessage());
		}

		return temas;

	}

	/**
	 * Servicio que guarda un tema en la base de datos
	 * 
	 * @param idTema
	 *            identificador del tema a guardar
	 * @param nombre
	 *            nombre del tema a guardar
	 * @param idCategoria
	 *            identificador de la categoria a la cual pertenece el tema a guardar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("guardarTema")
	public String guardarTema(
			@QueryParam("idTema") Integer idTema,
			@QueryParam("nombre") String nombre,
			@QueryParam("categoria") Integer idCategoria)
			throws RemoteException, MyException {
		tema = new TemaDto();
		log = Logger.getLogger(this.getClass());
		if (idTema == null || "".equals(idTema)) {
			return ("Debe ingresar identificador del tema");
		}
		if (nombre == null || "".equals(nombre)) {
			return ("Debe ingresar nombre del tema");
		}
		if (idCategoria == null || "".equals(idCategoria)) {
			return ("Debe ingresar categoria a la que pertenece el tema");
		}

		try {
			tema = temaLN.obtenerTema(idTema);
			if (tema != null) {
				return "Ya existe un tema con ese identificador";
			}
			temaLN.guardar(idTema, nombre, idCategoria);
		} catch (MyException e) {
			log.error("Error guardando categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return "Tema guardado exitosamente";

	}

	/**
	 * Servicio que actualiza un tema en la base de datos
	 * 
	 * @param idTema
	 *            identificador del tema a actualizar
	 * @param nombre
	 *            nombre del tema a actualizar
	 * @param idCategoria
	 *            identificador de la categoria a la cual pertenece el tema actualizado
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("actualizarTema")
	public String actualizarTema(
			@QueryParam("idTema") Integer idTema,
			@QueryParam("nombre") String nombre,
			@QueryParam("categoria") Integer idCategoria)
			throws RemoteException, MyException {
		tema = new TemaDto();
		log = Logger.getLogger(this.getClass());
		if (idTema == null || "".equals(idTema)) {
			return ("Debe ingresar identificador del tema");
		}
		if (nombre == null || "".equals(nombre)) {
			return ("Debe ingresar nombre del tema");
		}
		if (idCategoria == null || "".equals(idCategoria)) {
			return ("Debe ingresar categoria a la que pertenece el tema");
		}

		try {
			tema = temaLN.obtenerTema(idTema);
			if (tema == null) {
				return "El tema a actualizar no existe";
			}
			temaLN.actualizar(idTema, nombre, idCategoria);
		} catch (MyException e) {
			log.error("Error actualizando categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}

		return "Tema actualizado exitosamente";

	}

	
	//El siguiente servicio no esta disponible en este momento intente mas tarde.
	
	/**
	 * Servicio que elimina un tema en la base de datos
	 * 
	 * @param idTema
	 *            identificador del tema a eliminar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	
	//El siguiente servicio no se encuentra disponible, intente mas tarde. 
	/*@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("eliminarTema")
	public String eliminarTema(
			@QueryParam("idTema") Integer idTema)
			throws RemoteException, MyException {
		tema = new TemaDto();
		log = Logger.getLogger(this.getClass());
		if (idTema == null || "".equals(idTema)) {
			return ("Debe ingresar identificador del tema");
		}
		try {
			tema = temaLN.obtenerTema(idTema);
			if (tema == null) {
				return "El tema a eliminar no existe";
			}
			temaLN.eliminar(idTema);
		} catch (MyException e) {
			log.error("Error eliminando categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}

		return "Tema eliminado exitosamente";

	}*/

}