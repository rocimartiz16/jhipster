import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DiaJornada from './dia-jornada';
import DiaJornadaDetail from './dia-jornada-detail';
import DiaJornadaUpdate from './dia-jornada-update';
import DiaJornadaDeleteDialog from './dia-jornada-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DiaJornadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DiaJornadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DiaJornadaDetail} />
      <ErrorBoundaryRoute path={match.url} component={DiaJornada} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DiaJornadaDeleteDialog} />
  </>
);

export default Routes;
