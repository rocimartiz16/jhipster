import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActividadProyecto from './actividad-proyecto';
import ActividadProyectoDetail from './actividad-proyecto-detail';
import ActividadProyectoUpdate from './actividad-proyecto-update';
import ActividadProyectoDeleteDialog from './actividad-proyecto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActividadProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActividadProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActividadProyectoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActividadProyecto} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ActividadProyectoDeleteDialog} />
  </>
);

export default Routes;
