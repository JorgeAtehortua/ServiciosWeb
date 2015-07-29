//Se crean las variables contenedoras de las URL relacionadas con los servicios web

var URL_SERVICIO_OBTENER_ARCHIVOS = 'rest/Archivo/obtenerArchivos';
var URL_SERVICIO_OBTENERPORCATEGORIA_ARCHIVOS = 'rest/Archivo/obtenerArchivoPorCategoria';
var URL_SERVICIO_OBTENERPORCATEGORIAYTEMA_ARCHIVOS = 'rest/Archivo/obtenerArchivoPorCategoriaYTema';
var URL_SERVICIO_OBTENERCATEGORIA_CATEGORIA = 'rest/Categoria/obtenerCategorias';
var URL_SERVICIO_OBTENERTEMAPORCATEGORIA_TEMA = 'rest/Tema/obtenerTemasDeUnaCategoria';

//Se crea controlador controllerObtenerArchivos el cual sera el encargado 
//de procesar las diferentes tareas relacionadas con los archivos.

app.controller('controllerObtenerArchivos', function($scope, Archivo,
		$location) {
	//funcion para consumir el servicio wed para obtenerArchivos.
	$scope.obtenerArchivos = function() {
		Archivo.obtenerArchivos().success(function(data) {
			$scope.Archivos = data.archivoDto;
		})
	}
	//funcion para consumir el servicio wed de obtenerArchivoPorCategoria
	$scope.obtenerArchivoPorCategoria = function(idCategoria) {
		Archivo.obtenerArchivoPorCategoria(idCategoria).success(function(data) {
			$scope.categorias = data.archivoDto;
		})
	}
	//funcion para consumir el servicio wed de obtenerCategorias
	$scope.obtenerCategorias = function() {
		Archivo.obtenerCategorias().success(function(data) {
			$scope.categorias = data.categoriaDto;
		})
	}
	//funcion para consumir el servicio wed de obtenerTemasPorCategoria
	$scope.obtenerTemasPorCategoria = function(idCategoria) {
		Archivo.obtenerTemasDeUnaCategoria(idCategoria).success(function(data) {
			$scope.temas = data.temaDto;
		})
	}
	//inicializacion de las funciones primarias.
	$scope.obtenerArchivos();
	$scope.obtenerCategorias();
});

//Definicion de los diferentes servicios web para Archivo.

app.service('Archivo', function($http) {
	//Servicio web encargado de obtenerArchivos.
	this.obtenerArchivos = function() {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENER_ARCHIVOS,

		})
	};
	//Servicio web encargado de obtenerArchivoPorCategoria.
	this.obtenerArchivoPorCategoria = function(idCategoria) {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERPORCATEGORIA_ARCHIVOS,
			params : {
				idCategoria : idCategoria,
			}
		})
	};

	//Servicio web encargado de obtenerArchivoPorCategoriaYTema.
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
	//Servicio web encargado de obtenerCategorias.
	this.obtenerCategorias = function() {
		return $http({
			method : 'GET',
			url : URL_SERVICIO_OBTENERCATEGORIA_CATEGORIA,
			params : {

			}

		})
	};
	//Servicio web encargado de obtenerTemasDeUnaCategoria.
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

//Configura las vistas del aplicativo
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/Archivos', {
		templateUrl : 'archivos.html',
		controller : 'controllerObtenerArchivos'
	})
} ]);