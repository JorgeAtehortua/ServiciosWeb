package com.amhable.ws;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amhable.dominio.UsuarioDto;
import com.amhable.exception.MyException;
import com.amhable.logicaNegocio.UsuarioLN;

/**
 * Clase para el manejo de los servicios web relacionados con el usuario dentro
 * de nuesta logica del negocio.
 * 
 * @author Luisa Suarez
 * 
 */
@Path("Usuario")
@Component
public class UsuarioWs {

	private UsuarioLN usuarioLN;
	private Logger log;
	UsuarioDto usuario;

	/**
	 * @return the usuarioLN
	 */
	public UsuarioLN getUsuarioLN() {
		return usuarioLN;
	}

	/**
	 * @param usuarioLN
	 *            the usuarioLN to set
	 */
	public void setUsuarioLN(UsuarioLN usuarioLN) {
		this.usuarioLN = usuarioLN;
	}

	/**
	 * Servicio encargado de autenticar un usuario
	 * 
	 * @param idUsuario
	 *            del usuario a autenticar
	 * @param contrasena
	 *            del usuario a autenticar
	 * @return mensaje de informacion sobre el estado de la autenticacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("Login")
	public String autenticarUsuario(
			@QueryParam("idUsuario") String idUsuario,
			@QueryParam("contrasena") String contrasena)
			throws RemoteException, MyException {
		usuario = new UsuarioDto();
		log = Logger.getLogger(this.getClass());
		if (idUsuario == null || "".equals(idUsuario)) {
			return ("Debe ingresar identificador de la categoria");
		}
		if (contrasena == null || "".equals(contrasena)) {
			return ("Debe ingresar nombre de la categoria");
		}
		try {
			usuario = usuarioLN.obtenerUsuario(idUsuario);

			if (!usuario.getContrasena().equals(contrasena)) {
				return "Usuario NO autenticado, idUsuario y/o contraseña no son correctos";
			}

		} catch (MyException e) {
			log.error("Error autenticando usuario (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		return "Usuario autenticado exitosamente";

	}

	/**
	 * Servicio encargado de la consulta de los usuarios en la base de datos del
	 * proyecto
	 * 
	 * @param nombreUsuario
	 *            nombre el usuario a buscar.
	 * @return usuario que coincide con los criterios de la busqueda.
	 * @throws RemoteException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioDto obtenerUsuario(@QueryParam("nombre") String nombreUsuario)
			throws RemoteException {

		usuario = new UsuarioDto();
		log = Logger.getLogger(this.getClass());

		if (nombreUsuario == null || "".equals(nombreUsuario)) {
			// throw new MyException("Debe ingresar identificador del usuario");
		}
		try {
			usuario = usuarioLN.obtenerUsuario(nombreUsuario);

		} catch (MyException e) {
			// log.error("Error obteniento el usuario: ", e);
			throw new RemoteException(e.getMessage());
		}
		return usuario;
	}

	/**
	 * Servicio encargado guardar un usuario en la base de datos del proyecto
	 * 
	 * @param idUsuario
	 *            identificador del usuario a guardar
	 * @param contrasena
	 *            contraseña del usuario a guardar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("guardarUsuario")
	public String guardarUsuario(@QueryParam("idUsuario") String idUsuario,
			@QueryParam("contrasena") String contrasena)
			throws RemoteException, MyException {
		usuario = new UsuarioDto();
		log = Logger.getLogger(this.getClass());
		if (idUsuario == null || "".equals(idUsuario)) {
			return ("Debe ingresar identificador de la categoria");
		}
		if (contrasena == null || "".equals(contrasena)) {
			return ("Debe ingresar la contraseña del nuevo usuario");
		}
		try {
			usuario = usuarioLN.obtenerUsuario(idUsuario);
			if (usuario != null) {
				return "Ya existe un usuario con ese identificador";
			}
			usuarioLN.guardar(idUsuario, contrasena);

		} catch (MyException e) {
			log.error("Error guardando usuario (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		return "Usuario guardado exitosamente";

	}

	/**
	 * Servicio encargado de actualizar un usuario en la base de datos del
	 * proyecto
	 * 
	 * @param idUsuario
	 *            identificador del usuario a actualizar
	 * @param contrasena
	 *            contraseña del usuario a actualizar
	 * @return mensaje de informacion sobre el estado de la operacion
	 * @throws RemoteException
	 * @throws MyException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("actualizarUsuario")
	public String actualizarUsuario(@QueryParam("idUsuario") String idUsuario,
			@QueryParam("contrasena") String contrasena)
			throws RemoteException, MyException {
		usuario = new UsuarioDto();
		log = Logger.getLogger(this.getClass());
		if (idUsuario == null || "".equals(idUsuario)) {
			return ("Debe ingresar identificador del usuario");
		}
		if (contrasena == null || "".equals(contrasena)) {
			return ("Debe ingresar la contraseña del nuevo usuario");
		}
		try {
			usuario = usuarioLN.obtenerUsuario(idUsuario);
			if (usuario == null) {
				return "El usuario a actualizar no existe";
			}
			usuarioLN.actualizar(idUsuario, contrasena);

		} catch (MyException e) {
			log.error("Error actualizando usuario (WS): ", e);
			throw new RemoteException(e.getMessage());
		}
		return "Usuario actualizado exitosamente";
	}
}
