//Se crea el modulo y se le inyecta ngRoute que nos permite tener varias vistas
var appUsuarios = angular.module('Usuario', [ 'ngRoute', 'ngCookies' ]);

var URL_SERVICIO_LOGIN_USUARIO = 'rest/Usuario/Login';
var URL_SERVICIO_GUARDAR_USUARIO = 'rest/Usuario/guardarUsuario';

appUsuarios.controller('controllerLogin', function($scope, Usuario, $location) {
	$scope.login = function() {
		// Estos son los nombres de los campos en el HTML
		Usuario.validar($scope.idUsuario, $scope.contrasena).success(
				function(data) {
					if (!data.autenticado) {
						$scope.idUsuario = '';
						$scope.contrasena = '';
						return;
					} else
					$location.path('/registroDeUsuarios');
					
				}
				)
	}
})

appUsuarios.controller('controllerPrincipal',function($scope, Usuario, $location) {
	$scope.llamarLogin = function() {
	
		$location.path('/login');
	}
	
			
	
	$scope.llamarArchivo = function() {

		$location.path('/archivos')

	}
});
appUsuarios.controller('controllerRegistroUsuario', function($scope, Usuario, $location) {
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
					$location.path('/')
				})
	}
})

appUsuarios.service('Usuario', function($http) {

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
appUsuarios.config([ '$routeProvider', function($routeProvider) {
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
		templateUrl : 'registrarUsuario.html',
		controller : 'controllerRegistroUsuario'
	});

} ]);
