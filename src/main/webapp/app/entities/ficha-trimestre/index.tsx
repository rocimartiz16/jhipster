import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FichaTrimestre from './ficha-trimestre';
import FichaTrimestreDetail from './ficha-trimestre-detail';
import FichaTrimestreUpdate from './ficha-trimestre-update';
import FichaTrimestreDeleteDialog from './ficha-trimestre-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FichaTrimestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FichaTrimestreUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FichaTrimestreDetail} />
      <ErrorBoundaryRoute path={match.url} component={FichaTrimestre} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FichaTrimestreDeleteDialog} />
  </>
);

export default Routes;
