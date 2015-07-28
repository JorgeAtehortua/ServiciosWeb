package com.amhable.ws;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amhable.dominio.ArchivoDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.ArchivoLN;

/**
 * Clase que contiene los servicios para la tabla Archivo
 * 
 * @author Jorge Atehortua
 * 
 */
@Path("Archivo")
@Component
public class ArchivoWs {
	Logger log;

	/**
	 * Objeto tipo ArchivoDto con el que se manejan los datos de los archivos
	 */
	ArchivoDto archivo;
	/**
	 * Inyeccion de dependencias
	 */
	private ArchivoLN archivoLN;

	/**
	 * @return the archivoLN
	 */
	public ArchivoLN getArchivoLN() {
		return archivoLN;
	}

	/**
	 * @param archivoLN
	 *            the archivoLN to set
	 */
	public void setArchivoLN(ArchivoLN archivoLN) {
		this.archivoLN = archivoLN;
	}
	
	/**
	 * Servicio que obtiene la informacion de un archivo segun el identificador
	 * @param idArchivo del archivo a obtener
	 * @return objeto tipo ArchivoDto con la informacion del archivo obtenido
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("obtenerArchivo")
	public ArchivoDto obtenerArchivo(
			@QueryParam("idArchivo") Integer idArchivo)
			throws RemoteException, MyException {
		archivo = new ArchivoDto();
		log = Logger.getLogger(this.getClass());
		if (idArchivo == null || "".equals(idArchivo)) {
			throw new MyException("Debe ingresar identificador del archivo");
		}
		try {
			archivo = archivoLN.obtenerArchivo(idArchivo);
		} catch (MyException e) {
			log.error("Error obteniendo archivo (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (archivo == null) {
			throw new MyException(
					"No existe un archivo con el identificador ingresado");
		}
		return archivo;

	}
	
	/**
	 * Servicio que obtiene la lista con todos los archivos de la base de datos
	 * @return lista con todos los archivos de la base de datos
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerArchivos")
	public List<ArchivoDto> obtenerArchivos()throws RemoteException, MyException {
		List<ArchivoDto> archivos= new ArrayList<ArchivoDto>();
		log = Logger.getLogger(this.getClass());
		try {
			archivos = archivoLN.obtener();
		} catch (MyException e) {
			log.error("Error obteniendo la lista de archivos (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return archivos;

	}
	
	/**
	 * Servicio que  guarda un archivo en la base de datos
	 * @param idArchivo del archivo a guardar
	 * @param nombre del archivo a guardar
	 * @param contrasena del archivo a guardar
	 * @param rutaArchivo del archivo a guardar
	 * @param idCategoria del archivo a guardar
	 * @param idTema del archivo a guardar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("guardarArchivo")
	public String guardarArchivo(
			@QueryParam("idArchivo") Integer idArchivo,  
			@QueryParam("nombre")String nombre,
			@QueryParam("contrasena") String contrasena, 
			@QueryParam("rutaArchivo") String rutaArchivo, 
			@QueryParam("idCategoria") Integer idCategoria, 
			@QueryParam("idTema") Integer idTema)
			throws RemoteException, MyException {
		archivo = new ArchivoDto();
		log = Logger.getLogger(this.getClass());
		if (idArchivo == null || "".equals(idArchivo)) {
			throw new MyException("Debe ingresar identificador del archivo");
		}
		if (nombre == null || "".equals(nombre)) {
			throw new MyException("Debe ingresar nombre del archivo");
		}
		if (rutaArchivo == null || "".equals(rutaArchivo)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		if (idCategoria == null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		if (idTema == null || "".equals(idTema)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		
		try {
			archivo=archivoLN.obtenerArchivo(idArchivo);
			if(archivo!=null){
				return "Ya existe un archivo con ese identificador";
			}
			archivoLN.guardar(idArchivo, nombre, new Date(), contrasena, rutaArchivo, idCategoria, idTema);
			
		} catch (MyException e) {
			log.error("Error guardando archivo (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return "Archivo guardado exitosamente";

	}
	/**
	 * Servicio que actualiza un archivo en la base de datos
	 * @param idArchivo del archivo a actualizar
	 * @param nombre del archivo a actualizar
	 * @param contrasena del archivo a actualizar
	 * @param rutaArchivo del archivo a actualizar
	 * @param idCategoria del archivo a actualizar
	 * @param idTema del archivo a actualizar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("actualizarArchivo")
	public String actualizarArchivo(
			@QueryParam("idArchivo") Integer idArchivo,  
			@QueryParam("nombre")String nombre,
			@QueryParam("contrasena") String contrasena, 
			@QueryParam("rutaArchivo") String rutaArchivo, 
			@QueryParam("idCategoria") Integer idCategoria, 
			@QueryParam("idTema") Integer idTema)
			throws RemoteException, MyException {
		archivo = new ArchivoDto();
		log = Logger.getLogger(this.getClass());
		if (idArchivo == null || "".equals(idArchivo)) {
			throw new MyException("Debe ingresar identificador del archivo");
		}
		if (nombre == null || "".equals(nombre)) {
			throw new MyException("Debe ingresar nombre del archivo");
		}
		if (rutaArchivo == null || "".equals(rutaArchivo)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		if (idCategoria == null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		if (idTema == null || "".equals(idTema)) {
			throw new MyException("Debe ingresar ruta del archivo");
		}
		
		try {
			archivo=archivoLN.obtenerArchivo(idArchivo);
			if(archivo==null){
				return "El archivo a actualizar no existe";
			}
			archivoLN.actualizar(idArchivo, nombre, new Date(), contrasena, rutaArchivo, idCategoria, idTema);
			
		} catch (MyException e) {
			log.error("Error actualizando archivo (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return "Archivo actualizado exitosamente";

	}
//	
//	el servicio de eliminar Archivo no se encuentra disponible en este momento.
//	/**
//	 * Servicio que elimina un tema en la base de datos
//	 * @param idArchivo del archivo a eliminar
//	 * @return mensaje de informacion sobre el estado de la operacion
//	 * @throws RemoteException
//	 * @throws MyException
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("eliminarArchivo")
//	public String eliminarArchivo(
//			@QueryParam("idArchivo") Integer idArchivo)
//			throws RemoteException, MyException {
//		archivo = new ArchivoDto();
//		log = Logger.getLogger(this.getClass());
//		if (idArchivo == null || "".equals(idArchivo)) {
//			throw new MyException("Debe ingresar identificador del archivo");
//		}
//		try {
//			archivo = archivoLN.obtenerArchivo(idArchivo);
//			if(archivo==null){
//				return "El archivo a eliminar no existe";
//			}
//			archivoLN.eliminar(idArchivo);
//		} catch (MyException e) {
//			log.error("Error eliminando archivo (WS)", e);
//			throw new RemoteException(e.getMessage());
//		}
//		return "Archivo eliminado exitosamente";
//
//	}
	/**
	 * Servicio que obtiene los archivos pertenecientes a una categoria en la base de datos
	 * @param idCategoria a la que pertenecen los archivos a obtener
	 * @return lista de archivos pertenecientes al identificador de la categoria ingresado
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerArchivoPorCategoria")
	public List<ArchivoDto> obtenerArchivoPorCategoria(@QueryParam("idCategoria") Integer idCategoria)
			throws RemoteException, MyException {
		List<ArchivoDto> archivos= new ArrayList<ArchivoDto>();
		log = Logger.getLogger(this.getClass());
		if (idCategoria == null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar identificador de la categoria");
		}
		try {
			archivos = archivoLN.obtenerArchivosPorCategoria(idCategoria);
		} catch (MyException e) {
			log.error("Error obteniendo archivo por categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (archivos == null) {
			throw new MyException(
					"No existen archivos asociados a la identificador de la categoria ingresado");
		}
		return archivos;

	}
	
	/**
	 * Servicio que obtiene los archivos pertenecientes a una categoria y tema en la base de datos
	 * @param idCategoria a la que pertenecen los archivos a obtener
	 * @param idTema al que pertenecen los archivos a obtener
	 * @return lista de archivos pertenecientes al identificador de la categoria y tema ingresados
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerArchivoPorCategoriaYTema")
	public List<ArchivoDto> obtenerArchivoPorCategoriaYTema(
			@QueryParam("idCategoria") Integer idCategoria,
			@QueryParam("idTema") Integer idTema )
			throws RemoteException, MyException {
		List<ArchivoDto> archivos= new ArrayList<ArchivoDto>();
		log = Logger.getLogger(this.getClass());
		if (idCategoria == null || "".equals(idCategoria)) {
			throw new MyException("Debe ingresar identificador de la categoria");
		}
		if (idTema == null || "".equals(idTema)) {
			throw new MyException("Debe ingresar identificador del tema");
		}
		try {
			archivos = archivoLN.obtenerArchivoPorCategoriaYTema(idCategoria, idTema);
		} catch (MyException e) {
			log.error("Error obteniendo archivo por categoria y tema (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (archivos == null) {
			throw new MyException(
					"No existen archivos asociados a la identificador de la categoria y tema ingresados");
		}
		return archivos;

	}
	

}
