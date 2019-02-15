import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Anio from './anio';
import AnioDetail from './anio-detail';
import AnioUpdate from './anio-update';
import AnioDeleteDialog from './anio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Anio} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AnioDeleteDialog} />
  </>
);

export default Routes;
