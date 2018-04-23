import React from 'react';
import { Redirect, Route, Router } from 'react-router-dom';
import App from './App';
import Home from './Home/Home';
import Profile from './Profile/Profile';
import Callback from './Callback/Callback';
import Alarmas from './Alarmas/Alarmas';
import Auth from './Auth/Auth';
import Cerraduras from './Cerraduras/Cerraduras';
import Hubs from './Hubs/Hubs';
import Inmuebles from './Inmuebles/Inmuebles';
import UnidadesResidenciales from './UnidadesResidenciales/UnidadesResidenaciales';
import Floors from './Floors/Floors';
import Rooms from './Rooms/Rooms';
import history from './history';

const auth = new Auth();

const handleAuthentication = (nextState, replace) => {
  if (/access_token|id_token|error/.test(nextState.location.hash)) {
    auth.handleAuthentication();
  }
}

export const makeMainRoutes = () => {
  return (
    <Router history={history} component={App}>
        <div>
          <Route path="/" render={(props) => <App auth={auth} {...props} />} />
          <Route path="/home" render={(props) => <Home auth={auth} {...props} />} />
          <Route path="/profile" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Profile auth={auth} {...props} />
            )
          )} />
          <Route path="/floors" render={(props) => (
            !auth.isAuthenticated() || !auth.userHasRole(['admin']) ? (
              <Redirect to="/home"/>
            ) : (
              <Floors auth={auth} {...props} />
            )
          )} />
          <Route path="/alarmas" render={(props) => (
            !auth.isAuthenticated() || (!auth.userHasRole(['admin']) && !auth.userHasRole(['yale']) && !auth.userHasRole(['segPrivada']) && !auth.userHasRole(['propietario'])) ? (
              <Redirect to="/home"/>
            ) : (
              <Alarmas auth={auth} {...props} />
            )
          )} />
          <Route path="/cerraduras" render={(props) => (
            !auth.isAuthenticated() || (!auth.userHasRole(['admin']) && !auth.userHasRole(['segPrivada']) && !auth.userHasRole(['propietario'])) ? (
              <Redirect to="/home"/>
            ) : (
              <Cerraduras auth={auth} {...props} />
            )
          )} />
          <Route path="/hubs" render={(props) => (
            !auth.isAuthenticated() || !auth.userHasRole(['yale']) ? (
              <Redirect to="/home"/>
            ) : (
              <Hubs auth={auth} {...props} />
            )
          )} />
          <Route path="/inmuebles" render={(props) => (
            !auth.isAuthenticated() || (!auth.userHasRole(['admin']) && !auth.userHasRole(['yale']) && !auth.userHasRole(['segPrivada']) && !auth.userHasRole(['propietario'])) ? (
              <Redirect to="/home"/>
            ) : (
              <Inmuebles auth={auth} {...props} />
            )
          )} />
          <Route path="/unidadesResidenciales" render={(props) => (
            !auth.isAuthenticated() || (!auth.userHasRole(['admin']) && !auth.userHasRole(['yale']) && !auth.userHasRole(['segPrivada'])) ? (
              <Redirect to="/home"/>
            ) : (
              <UnidadesResidenciales auth={auth} {...props} />
            )
          )} />
          <Route path="/rooms" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Rooms auth={auth} {...props} />
            )
          )} />
          <Route path="/callback" render={(props) => {
            handleAuthentication(props);
            return <Callback {...props} /> 
          }}/>        
        </div>
      </Router>
  );
}
