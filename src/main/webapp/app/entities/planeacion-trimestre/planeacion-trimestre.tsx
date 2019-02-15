import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './planeacion-trimestre.reducer';
import { IPlaneacionTrimestre } from 'app/shared/model/planeacion-trimestre.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlaneacionTrimestreProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PlaneacionTrimestre extends React.Component<IPlaneacionTrimestreProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { planeacionTrimestreList, match } = this.props;
    return (
      <div>
        <h2 id="planeacion-trimestre-heading">
          <Translate contentKey="ghostceetApp.planeacionTrimestre.home.title">Planeacion Trimestres</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.planeacionTrimestre.home.createLabel">Create new Planeacion Trimestre</Translate>
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
                  <Translate contentKey="ghostceetApp.planeacionTrimestre.trimestre">Trimestre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {planeacionTrimestreList.map((planeacionTrimestre, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${planeacionTrimestre.id}`} color="link" size="sm">
                      {planeacionTrimestre.id}
                    </Button>
                  </td>
                  <td>
                    {planeacionTrimestre.trimestre ? (
                      <Link to={`trimestre/${planeacionTrimestre.trimestre.id}`}>{planeacionTrimestre.trimestre.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${planeacionTrimestre.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${planeacionTrimestre.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${planeacionTrimestre.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ planeacionTrimestre }: IRootState) => ({
  planeacionTrimestreList: planeacionTrimestre.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlaneacionTrimestre);
