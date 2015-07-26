//Se crea el modulo y se le inyecta ngRoute que nos permite tener varias vistas
var appUsuarios=angular.module('Usuario', ['ngRoute', 'ngCookies']);

var URL_SERVICIO_LOGIN_USUARIO = 'rest/Usuario/Login';
var URL_SERVICIO_GUARDAR_USUARIO = 'rest/Usuario/guardarUsuario';

appUsuarios.controller('controllerLogin', function ($scope, Usuario){
	$scope.login=function(){
		//Estos son los nombres de los campos en el HTML
		Usuario.login($scope.idUsuario, $scope.contrasena).success(function(data){
			if(data!=''){
				alert(data);
				$scope.idUsuario='';
				$scope.contrasena='';
				return;
			}else
				alert("Bienvenido");
			$location.url('/registroDeUsuarios')
		})
	}
})

appUsuarios.controller('controllerRegistroUsuario', function ($scope, Usuario){
	$scope.guardarUsuario=function(){
		//Estos son los nombres de los campos en el HTML
		Usuario.guardarUsuario($scope.idUsuario, $scope.contrasena).success(function(data){
			if(data!=''){
				alert(data);
				$scope.idUsuario='';
				$scope.contrasena='';
				return;
			}else
				alert("Usuario Registrado con exito!");
			$location.url('/principal')
		})
	}
})

appUsuarios.service('Usuario', function($http){
	
	this.login=function(usuario,contrasena){
		return $http({
			method:'GET',
			url: URL_SERVICIO_LOGIN_USUARIO,
			params:{
				idUsuario: usuario,
				contrasena: contrasena,
			}
		});
	};
	
	this.guardarUsuario=function(usuario,contrasena){
		return $http({
			method:'POST',
			url: URL_SERVICIO_GUARDAR_USUARIO,
			params:{
				idUsuario:usuario,
				contrasena: contrasena,
			}
		});
	};
	
});
//Configura las vistas del aplicativo
appClientes.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'login.html', 
		controller : 'controllerLogin'
	});
	$routeProvider.when('/principal', {
		templateUrl : 'principal.html', 
		//controller : 'controller'
	});
	$routeProvider.when('/registroDeUsuarios', {
		templateUrl : 'registrarUsuario.html',
		controller :  'controllerRegistroUsuario'
	})
	
} ]);





