import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FaseProyecto from './fase-proyecto';
import FaseProyectoDetail from './fase-proyecto-detail';
import FaseProyectoUpdate from './fase-proyecto-update';
import FaseProyectoDeleteDialog from './fase-proyecto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FaseProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FaseProyectoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FaseProyectoDetail} />
      <ErrorBoundaryRoute path={match.url} component={FaseProyecto} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FaseProyectoDeleteDialog} />
  </>
);

export default Routes;
