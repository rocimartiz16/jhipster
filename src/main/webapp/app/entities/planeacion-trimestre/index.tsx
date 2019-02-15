import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaneacionTrimestre from './planeacion-trimestre';
import PlaneacionTrimestreDetail from './planeacion-trimestre-detail';
import PlaneacionTrimestreUpdate from './planeacion-trimestre-update';
import PlaneacionTrimestreDeleteDialog from './planeacion-trimestre-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaneacionTrimestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaneacionTrimestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaneacionTrimestreDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaneacionTrimestre} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PlaneacionTrimestreDeleteDialog} />
  </>
);

export default Routes;
