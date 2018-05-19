var app = angular.module('panelcontrol', []);

app.controller('control', function control($scope, $interval){
	$scope.panelActual = 1;
	$scope.inmuebles = [
		{
			id: 1, 
			alarma: "",
			fallo: '',
			color: '#34B92C',
			alerta: {},
			falla: {},
			tiempoAlerta: 0,
			info: {
				propietario: 'Nicolás Sanabria',
				telefono: '98543846',
				email: 'correofalso123@uniandes.edu.co'
			}, 
			historial: []
		}, {
			id: 2,
			alarma: "",
			color: '#34B92C',
			alerta: {},
			info: {
				propietario: 'El David',
				telefono: '52136945',
				email: 'davidgal@uniandes.edu.co'
			}, 
			historial: []
		}, {
			id: 3,
			alarma: "",
			color: '#34B92C',
			alerta: {},
			info: {
				propietario: 'El JuanK',
				telefono: '84737483',
				email: 'juank@uniandes.edu.co'
			}, 
			historial: []
		}, {
			id: 4,
			alarma: "",
			color: '#34B92C',
			alerta: {},
			info: {
				propietario: 'La Rusa',
				telefono: '36473658',
				email: 'larusa@uniandes.edu.co'
			}, 
			historial: []
		}, {
			id: 5,
			alarma: "",
			color: '#34B92C',
			alerta: {},
			info: {
				propietario: 'Mamasita La profe',
				telefono: '12345678',
				email: 'laprofe@uniandes.edu.co'
			}, 
			historial: []
		}, {
			id: 6,
			alarma: "",
			color: '#34B92C',
			alerta: {},
			info: {
				propietario: 'El Perrito',
				telefono: '545132545',
				email: 'elperrito@uniandes.edu.co'
			}, 
			historial: []
		}
	];

	$scope.inmuebleActual = {};
	$scope.mostrarAlertas = true;
	$scope.mostrarFallos = true;

	/*$scope.io = require('socket.io')(80);
	$scope.cfg = require('./config.json');
	$scope.tw = require('node-tweet-stream')(cfg);
	$scope.tw.track('socket.io');
	$scope.tw.track('javascript');
	$scope.tw.on('tweet', function(tweet){
	  io.emit('tweet', tweet);
	});*/


	$scope.socket = io.connect(location.protocol + '//' + /*document.domain*/ '172.24.42.52' + ':' + /*location.port*/ '8086' + '/thermalcomfort');
	$scope.socket.on('mensajes', function(msg){
		
		//$('#historia').append(msg + '<br/>');
		if (msg.includes('HUB FUERA DE LINEA') || msg.includes('CERRADURA FUERA DE LINEA')){
			msg = 'FALLO: ' + msg;
			$scope.inmuebles[0].fallo = msg;
			$scope.fallar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('HUB EN LINEA') || msg.includes('CERRADURA EN LINEA')){
         	$scope.inmuebles[0].fallo = '';
			$scope.inmuebles[0].color = '#34B92C';
			$scope.detenerFallar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Puerta abierta demasiado tiempo!!')){
			$scope.inmuebles[0].alarma = msg;
			$scope.alertar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Puerta cerrada!!') || msg.includes('ALARMA:Puerta cerrada')){
			$scope.inmuebles[0].alarma = '';
			$scope.detenerAlertar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Numero de intentos excedido')){
			$scope.inmuebles[0].alarma = msg;
			$scope.alertar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Horario no permitido')){
			$scope.inmuebles[0].alarma = msg;
			$scope.alertar(0);
			$scope.guardarMensaje(msg);
		}  else if (msg.includes('ALARMA:Sistema bloqueado')){
			$scope.inmuebles[0].alarma = msg;
			$scope.alertar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Sistema desbloqueado')){
			$scope.inmuebles[0].alarma = '';
			$scope.detenerAlertar(0);
			$scope.guardarMensaje(msg);
		} else if (msg.includes('ALARMA:Bateria baja')){
			$scope.inmuebles[0].alarma = msg;
			$scope.alertar(0);
			$scope.guardarMensaje(msg);
		}
	});

	$scope.guardarMensaje = function(msg){
		f = new Date();
		$scope.inmuebles[0].historial.push(f.getFullYear() + '/' + (f.getMonth() + 1) + '/' + f.getDate() + ' ' + f.getHours() + ':' + f.getMinutes() + ':' + f.getSeconds() + ' ' + msg);
	};

	$scope.panelSeleccionado = function(id){
		return $scope.panelActual == id;
	};

	$scope.cambiarPanelActual = function(id){
		$scope.panelActual = id;
	};

	$scope.seleccionarInmueble = function(inmueble){
		$scope.inmuebleActual = inmueble;
	};

	$scope.alertar = function(id){

		$scope.$applyAsync(function () {
			$scope.inmuebles[id].alerta = $interval(function(){
				if ($scope.mostrarAlertas){
					if ($scope.inmuebles[id].color == '#0F4AC7'){
						$scope.inmuebles[id].color = '#DE1D06';
					} else {
						$scope.inmuebles[id].color = '#0F4AC7'
					}

					if ($scope.inmuebles[id].alarma == 'ALARMA:Numero de intentos excedido'){
						if ($scope.inmuebles[id].tiempoAlerta == 10){
							$scope.inmuebles[id].alarma = '';
							$scope.detenerAlertar(id);
							$scope.inmuebles[id].tiempoAlerta = 0;
						}

						$scope.inmuebles[id].tiempoAlerta++;
					} else if ($scope.inmuebles[id].alarma == 'ALARMA:Horario no permitido'){
						if ($scope.inmuebles[id].tiempoAlerta == 40){
							$scope.inmuebles[id].alarma = '';
							$scope.detenerAlertar(id);
							$scope.inmuebles[id].tiempoAlerta = 0;
						}

						$scope.inmuebles[id].tiempoAlerta++;
					}

					if ($scope.inmuebles[id].alarma == 'ALARMA:Bateria baja'){
						if ($scope.inmuebles[id].tiempoAlerta == 10){
							$scope.inmuebles[id].alarma = '';
							$scope.detenerAlertar(id);
							$scope.inmuebles[id].tiempoAlerta = 0;
						}

						$scope.inmuebles[id].tiempoAlerta++;
					}
				} else {
					$scope.inmuebles[id].color = '#34B92C';
				}
			}, 500); 
		});
	};

	$scope.fallar = function(id){
		$scope.$applyAsync(function () {
			$scope.inmuebles[id].falla = $interval(function(){
				if ($scope.mostrarFallos){
					if ($scope.inmuebles[id].color == '#0F4AC7'){
						$scope.inmuebles[id].color = '#DE1D06';
					} else {
						$scope.inmuebles[id].color = '#0F4AC7'
					}
				} else {
					$scope.inmuebles[id].color = '#34B92C';
				}
			}, 500); 
		});
	};

	$scope.detenerAlertar = function(id){
		$scope.$applyAsync(function () {
			$interval.cancel($scope.inmuebles[id].alerta);
			$scope.inmuebles[id].color = '#34B92C';
		});
	};

	$scope.detenerFallar = function(id){
		$scope.$applyAsync(function () {
			$interval.cancel($scope.inmuebles[id].falla);
			$scope.inmuebles[id].color = '#34B92C';
		});
	};
});

