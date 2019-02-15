import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VinculacionInstructor from './vinculacion-instructor';
import VinculacionInstructorDetail from './vinculacion-instructor-detail';
import VinculacionInstructorUpdate from './vinculacion-instructor-update';
import VinculacionInstructorDeleteDialog from './vinculacion-instructor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VinculacionInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VinculacionInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VinculacionInstructorDetail} />
      <ErrorBoundaryRoute path={match.url} component={VinculacionInstructor} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VinculacionInstructorDeleteDialog} />
  </>
);

export default Routes;
