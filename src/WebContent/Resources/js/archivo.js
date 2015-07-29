//Se crea el modulo y se intecta ngRoute que nos eprmite tener varias vistas
//var appArchivos=angular.module('Archivo',[ 'ngRoute', 'ngCookies' ]);

var URL_SERVICIO_OBTENER_ARCHIVOS = 'rest/Archivo/obtenerArchivos';
var URL_SERVICIO_OBTENERPORCATEGORIA_ARCHIVOS = 'rest/Archivo/obtenerArchivoPorCategoria';
var URL_SERVICIO_OBTENERPORCATEGORIAYTEMA_ARCHIVOS = 'rest/Archivo/obtenerArchivoPorCategoriaYTema';

var URL_SERVICIO_OBTENERCATEGORIA_CATEGORIA = 'rest/Categoria/obtenerCategorias';
var URL_SERVICIO_OBTENERTEMAPORCATEGORIA_TEMA = 'rest/Tema/obtenerTemasDeUnaCategoria';

appUsuarios.controller('controllerObtenerArchivos', function($scope, Archivo,
		$location) {
	$scope.obtenerArchivos = function() {
		Archivo.obtenerArchivos().success(function(data) {
			$scope.Archivos = data.archivoDto;
		})
	}
	$scope.obtenerArchivoPorCategoria = function(idCategoria) {
		Archivo.obtenerArchivoPorCategoria(idCategoria).success(function(data) {
			$scope.categorias = data.archivoDto;
		})
	}
	$scope.obtenerCategorias = function() {
		Archivo.obtenerCategorias().success(function(data) {
			$scope.categorias = data.categoriaDto;
		})
	}
	$scope.obtenerTemasPorCategoria = function(idCategoria) {
		Archivo.obtenerTemasDeUnaCategoria(idCategoria).success(function(data) {
			$scope.temas = data.temaDto;
		})
	}
	$scope.obtenerArchivos();
	$scope.obtenerCategorias();
});

appUsuarios.service('Archivo', function($http) {
	this.obtenerArchivos = function() {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENER_ARCHIVOS,

		})
	};

	// Nombre del campo en el HTML idCategoria
	this.obtenerArchivoPorCategoria = function(idCategoria) {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERPORCATEGORIA_ARCHIVOS,
			params : {
				idCategoria : idCategoria,
			}
		})
	};

	// Nombre del campo en el HTML idCategoria, idTema
	this.obtenerArchivoPorCategoriaYTema = function(idCategoria, idTema) {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERPORCATEGORIAYTEMA_ARCHIVOS,
			params : {
				idCategoria : idCategoria,
				idTema : idTema,
			}
		})
	};

	this.obtenerCategorias = function() {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERCATEGORIA_CATEGORIA,
			params : {

			}

		})
	};
	this.obtenerTemasDeUnaCategoria = function(idCategoria) {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERTEMAPORCATEGORIA_TEMA,
			params : {
				idCategoria : idCategoria,

			}

		})
	};

});

appUsuarios.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/Archivos', {
		templateUrl : 'archivos.html',
		controller : 'controllerObtenerArchivos'
	})
} ]);