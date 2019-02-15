import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DisponibilidadCompetencias from './disponibilidad-competencias';
import DisponibilidadCompetenciasDetail from './disponibilidad-competencias-detail';
import DisponibilidadCompetenciasUpdate from './disponibilidad-competencias-update';
import DisponibilidadCompetenciasDeleteDialog from './disponibilidad-competencias-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DisponibilidadCompetenciasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DisponibilidadCompetenciasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DisponibilidadCompetenciasDetail} />
      <ErrorBoundaryRoute path={match.url} component={DisponibilidadCompetencias} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DisponibilidadCompetenciasDeleteDialog} />
  </>
);

export default Routes;
