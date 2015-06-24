/**
 * 
 */
package com.amhable.ws;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.amhable.dominio.UsuarioDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.UsuarioLN;
import com.amhable.logicaNegocio.imp.UsuarioLNimp;

/**
 * @author Black_Dog
 *
 */
@Path ("usuario")
@Component
public class UsuarioWs {
	
	
	private UsuarioLN usuarioLN;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioDto obtenerUsuario(@QueryParam("nombre") String nombrePersona)throws RemoteException{
		
		UsuarioDto usuario=new UsuarioDto();
		//String idUsuario=" ";
		//String clave=" ";
		
		try {
			usuario = usuarioLN.obtenerUsuario(nombrePersona);
			//idUsuario = usuario.getIdUsuario();
			//clave =usuario.getContrasena();
			
		} catch (MyException e) {
			throw new RemoteException(e.getMessage());
		}
		return usuario;//"Usuario: " + usuario.getIdUsuario()+ ": " + usuario.getContrasena();
	}
	/*
	@GET
	@Produces(MediaType.TEXT_HTML)
	//public String saludar(@QueryParam("nombre") String nombrePersona){
	@Path("{nombre}")
	public String saludar(@PathParam("nombre") String nombrePersona){,
			@PathParam("apellido") String apellido){
		return "hola   " + nombrePersona + "" + apellido;
	}*/

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
}
