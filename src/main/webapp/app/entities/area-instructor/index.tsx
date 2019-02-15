import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AreaInstructor from './area-instructor';
import AreaInstructorDetail from './area-instructor-detail';
import AreaInstructorUpdate from './area-instructor-update';
import AreaInstructorDeleteDialog from './area-instructor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AreaInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AreaInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AreaInstructorDetail} />
      <ErrorBoundaryRoute path={match.url} component={AreaInstructor} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AreaInstructorDeleteDialog} />
  </>
);

export default Routes;
