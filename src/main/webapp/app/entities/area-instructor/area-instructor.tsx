import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './area-instructor.reducer';
import { IAreaInstructor } from 'app/shared/model/area-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAreaInstructorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class AreaInstructor extends React.Component<IAreaInstructorProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { areaInstructorList, match } = this.props;
    return (
      <div>
        <h2 id="area-instructor-heading">
          <Translate contentKey="ghostceetApp.areaInstructor.home.title">Area Instructors</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.areaInstructor.home.createLabel">Create new Area Instructor</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.areaInstructor.estado">Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.areaInstructor.area">Area</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.areaInstructor.instructor">Instructor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {areaInstructorList.map((areaInstructor, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${areaInstructor.id}`} color="link" size="sm">
                      {areaInstructor.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`ghostceetApp.Estado.${areaInstructor.estado}`} />
                  </td>
                  <td>{areaInstructor.area ? <Link to={`area/${areaInstructor.area.id}`}>{areaInstructor.area.nombreArea}</Link> : ''}</td>
                  <td>
                    {areaInstructor.instructor ? (
                      <Link to={`instructor/${areaInstructor.instructor.id}`}>{areaInstructor.instructor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${areaInstructor.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${areaInstructor.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${areaInstructor.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ areaInstructor }: IRootState) => ({
  areaInstructorList: areaInstructor.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AreaInstructor);
