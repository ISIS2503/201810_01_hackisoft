
<!doctype html>
<html lang="es" ng-app="panelcontrol">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--<link rel="icon" href="../../../../favicon.ico">-->

    <title>Panel de control - Hackisoft</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/panelcontrol.css" rel="stylesheet">
  </head>

  <body ng-controller="control">
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
      <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Hackisoft</a>
      <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
          <a class="nav-link" href="#">Cerrar sesión</a>
        </li>
      </ul>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
          <div class="sidebar-sticky">
            <ul class="nav flex-column">
              <li class="nav-item">
                <a class="nav-link" ng-class="{active: panelSeleccionado(1)}" href ng-click="cambiarPanelActual(1)">
                  <span data-feather="map"></span>
                  Mapa
                </a>
              </li>
            </ul>
            <div id="historia"></div>
          </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

          <div class="row" ng-show="panelSeleccionado(1)">
          	<div class="col-1" style="background-color: #1D6612"><button ng-click="alertar(0)">A</button><button ng-click="detenerAlertar(0)">D</button></div>
          	<div class="col-10">
          		<div style="background-color: #000000; float: left; width: 100%; text-align: center; color: #FFFFFF">
	          		<h5><label><input type="checkbox" ng-model="mostrarAlertas"> Alertas</label> | <label><input type="checkbox" ng-model="mostrarFallos"> Fallos</label></h5>
	          	</div> 
          		<a href data-toggle="modal" data-target="#infoInmueble" ng-repeat="inmueble in inmuebles" ng-click="seleccionarInmueble(inmueble)" style="border-radius: 15px; background-color: {{inmueble.color}}; color: #FFFFFF; width: 35%; margin: 60px; padding: 30px; margin-top: 10px; margin-bottom: 10px; float: left">
          			<h3>Inmueble {{inmueble.id}}</h3>
          			<strong ng-show="mostrarAlertas">{{inmueble.alarma}}</strong><br/>
          			<strong ng-show="mostrarFallos">{{inmueble.fallo}}</strong>
          		</a>
          		<div style="background-color: #000000; float: left; width: 100%; text-align: center; color: #FFFFFF"><h6>Entrada</h6></div>         		
          		<hr/>
          	</div>
          	<div class="col-1" style="background-color: #1D6612">-</div>
          	<hr/>
          	
          </div>

          <div class="row" ng-show="panelSeleccionado(2)">
          	<h2>Alertas</h2>
          </div>

          <div class="row" ng-show="panelSeleccionado(3)">
          	<h2>Inmuebles</h2>
          </div>

          <div class="row" ng-show="panelSeleccionado(4)">
          	<h2>Usuarios</h2>
          </div>
        </main>
      </div>
    </div>

	<div class="modal" tabindex="-1" role="dialog" id="infoInmueble">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Inmueble {{inmuebleActual.id}}</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p><strong>Propietario</strong>: {{inmuebleActual.info.propietario}}</p>
	        <p><strong>Teléfono</strong>: {{inmuebleActual.info.telefono}}</p>
	        <p><strong>Email</strong>: {{inmuebleActual.info.email}}</p>

	        <br/>
	        <h4>Historial</h4>
	        <p ng-repeat="hist in inmuebleActual.historial">{{hist}}</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
	      </div>
	    </div>
	  </div>
	</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-3.3.1.slim.min.js"></script>
    <script src="js/angular.min.js"></script>
    <script src="js/socket.io.js"></script>
    <script src="js/app.js"></script>

    <!-- Icons -->
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script>
      feather.replace()
    </script>
  </body>
</html>
