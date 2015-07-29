
/**
 * @author Jorge Atehortua
 * @author Luisa Suarez
 */

//Se crea el modulo y se le inyecta ngRoute que nos permite tener varias vistas
var app = angular.module('Usuario', [ 'ngRoute', 'ngCookies' ]);

//creamos las variables en las cuales tendremos las URL relacionadas con los diferentes servicios.
var URL_SERVICIO_LOGIN_USUARIO = 'rest/Usuario/Login';
var URL_SERVICIO_GUARDAR_USUARIO = 'rest/Usuario/guardarUsuario';

//Definimos el controlador para login, el cual sera el encargado de manejar el proceso de validacion.
app.controller('controllerLogin', function($scope, Usuario, $location){
	$scope.login = function(){
		// Parametros de entrada para el servicio
		Usuario.validar($scope.idUsuario, $scope.contrasena).success(
			function(data){
				if (!data.autenticado) {
					$scope.idUsuario = '';
					$scope.contrasena = '';
					if (!data.datos) {
						alert("Los campos son obligatorios");
					}else alert("Usuario no existe");
					return;
				} else
				$location.path('/registroDeUsuarios');
			}
		)
	}
});

//Definimos el controlador para la ventana principal.
app.controller('controllerPrincipal',function($scope, Usuario, $location) {
	$scope.llamarLogin = function() {
		$location.path('/login');
	}
	$scope.llamarArchivo = function() {
		$location.path('/archivos');
	}
	$scope.llamarPrincipal = function() {
		$location.path('/');
	}
});

//Definimos el controlador para Registro de usuarios.
app.controller('controllerRegistroUsuario', function($scope, Usuario, $location) {
	$scope.guardarUsuario = function() {
		Usuario.guardarUsuario($scope.Usuario.idUsuario, $scope.Usuario.contrasena).success(
			function(data) {
				if (!data.guardado) {
					$scope.Usuario.idUsuario = '';
					$scope.Usuario.contrasena = '';
					if (!data.datos) {
						alert("Los campos son obligatorios");
					}else alert("Ya existe un usuario con ese nombre");
					return;
				} else
					alert("Usuario Registrado con exito!");
				$location.path('/');
			})
	}
});

//creacion del llamado al servicio Usuario, encargado de llamar los servicios de usuario.
app.service('Usuario', function($http) {

	//Servicio web para validar el login.
	//Recibe el usuario y la contraseña ingresados.
	this.validar = function(usuario, contrasena) {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_LOGIN_USUARIO,
			params : {
				idUsuario : usuario,
				contrasena : contrasena,
			}
		});
	};

	//Servicio web encargado de guardar un usuario nuevo.
	//Recibe el usuario y la contraseña ingresados.
	this.guardarUsuario = function(idUsuario, contrasena) {
		return $http({
			method : 'POST',
			url : URL_SERVICIO_GUARDAR_USUARIO,
			params : {
				idUsuario : idUsuario,
				contrasena : contrasena
			}
		});
	};
});


// Configura las vistas del aplicativo
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'login.html',
		controller : 'controllerLogin'
	});
	$routeProvider.when('/archivos', {
		templateUrl : 'archivos.html',
		controller : 'controllerObtenerArchivos'
	});
	$routeProvider.when('/', {
		templateUrl : 'principal.html',
		controller : 'controllerPrincipal'
	});
	$routeProvider.when('/registroDeUsuarios', {
		templateUrl : 'registrarUsuario.html',
		controller : 'controllerRegistroUsuario'
	});
	$routeProvider.otherwise({
		templateUrl : 'principal.html',
		controller : 'controllerprincipal'
	});
}]);
