package com.amhable.ws;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amhable.dominio.UsuarioDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.UsuarioLN;


/**
 * Clase para el manejo de los servicios web relacionados con el usuario
 * dentro de nuesta logica del negocio.
 * 
 * @author Jorge Atehortua
 * 
 */
@Path ("Usuario")
@Component
public class UsuarioWs {
	
	
	private UsuarioLN usuarioLN;
	private Logger log;
	/**
	 * @return the usuarioLN
	 */
	public UsuarioLN getUsuarioLN() {
		return usuarioLN;
	}

	/**
	 * @param usuarioLN the usuarioLN to set
	 */
	public void setUsuarioLN(UsuarioLN usuarioLN) {
		this.usuarioLN = usuarioLN;
	}
	
	/**
	 * Servicio encargado de la consulta de los usuarios en la base de datos del proyecto
	 * 
	 * @param nombreUsuario es el usuario a buscar.
	 * @return usuario que coincide con los criterios de la busqueda.
	 * @throws RemoteException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioDto obtenerUsuario(@QueryParam("nombre") String nombreUsuario)throws RemoteException{
		
		UsuarioDto usuario=new UsuarioDto();
		log = Logger.getLogger(this.getClass());
		
		if (nombreUsuario == null || "".equals(nombreUsuario)) {
			//throw new MyException("Debe ingresar identificador del usuario");
		}
		try {
			usuario = usuarioLN.obtenerUsuario(nombreUsuario);
			
		} catch (MyException e) {
			//log.error("Error obteniento el usuario: ", e);
			throw new RemoteException(e.getMessage());
		}
		return usuario;
	}
/*	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarUsuario(@QueryParam("nombre") String nombreUsuario, @QueryParam("pass") String contrasena) throws RemoteException, MyException{
		
		
		return null;
	}*/
}
