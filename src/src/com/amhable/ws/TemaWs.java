/**
 * 
 */
package com.amhable.ws;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
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
 * @author Luisa
 *
 */
@Path ("Tema")
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
	 * @param temaLN the temaLN to set
	 */
	public void setTemaLN(TemaLN temaLN) {
		this.temaLN = temaLN;
	}
	
	/**
	 * Servicio que obtiene la informacion de un tema segun el identificador
	 * @param identificador del tema a obtener
	 * @return objeto tipo TemaDto con la informacion del tema obtenido
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerTema")
	public TemaDto obtenerTema(@QueryParam("idTema")Integer idTema) throws RemoteException, MyException{
		tema= new TemaDto();
		log= Logger.getLogger(this.getClass());
		if (idTema == null || "".equals(idTema)) {
			throw new MyException("Debe ingresar identificador del tema");
		}
		try{
			tema=temaLN.obtenerTema(idTema);
					}catch(MyException e){
			log.error("Error obteniendo categoria (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		if (tema == null) {
			throw new MyException("No existe un tema con el identificador ingresado");
		}
		return tema;
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("obtenerTemas")
	public List<TemaDto> obtenerTemas() throws RemoteException, MyException{
		List<TemaDto> temas= new ArrayList<TemaDto>();
		log= Logger.getLogger(this.getClass());
		
		try{
			temas=temaLN.obtenerTemas();
		}catch(MyException e){
			log.error("Error obteniendo la lista de temas (WS)", e);
			throw new RemoteException(e.getMessage());
		}
		
		return temas;
		
	}
	

}