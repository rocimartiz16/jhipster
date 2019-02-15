import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import JornadaInstructor from './jornada-instructor';
import JornadaInstructorDetail from './jornada-instructor-detail';
import JornadaInstructorUpdate from './jornada-instructor-update';
import JornadaInstructorDeleteDialog from './jornada-instructor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JornadaInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JornadaInstructorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JornadaInstructorDetail} />
      <ErrorBoundaryRoute path={match.url} component={JornadaInstructor} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={JornadaInstructorDeleteDialog} />
  </>
);

export default Routes;
