import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaneacionActividad from './planeacion-actividad';
import PlaneacionActividadDetail from './planeacion-actividad-detail';
import PlaneacionActividadUpdate from './planeacion-actividad-update';
import PlaneacionActividadDeleteDialog from './planeacion-actividad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaneacionActividadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaneacionActividadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaneacionActividadDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaneacionActividad} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PlaneacionActividadDeleteDialog} />
  </>
);

export default Routes;
