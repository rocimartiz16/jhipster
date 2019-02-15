import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vinculacion-instructor.reducer';
import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IVinculacionInstructorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IVinculacionInstructorState = IPaginationBaseState;

export class VinculacionInstructor extends React.Component<IVinculacionInstructorProps, IVinculacionInstructorState> {
  state: IVinculacionInstructorState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { vinculacionInstructorList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="vinculacion-instructor-heading">
          <Translate contentKey="ghostceetApp.vinculacionInstructor.home.title">Vinculacion Instructors</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.vinculacionInstructor.home.createLabel">Create new Vinculacion Instructor</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fechaInicio')}>
                  <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaInicio">Fecha Inicio</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fechaFin')}>
                  <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaFin">Fecha Fin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.vinculacionInstructor.instructor">Instructor</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.vinculacionInstructor.anio1">Anio 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.vinculacionInstructor.vinculacion">Vinculacion</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vinculacionInstructorList.map((vinculacionInstructor, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vinculacionInstructor.id}`} color="link" size="sm">
                      {vinculacionInstructor.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={vinculacionInstructor.fechaInicio} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={vinculacionInstructor.fechaFin} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {vinculacionInstructor.instructor ? (
                      <Link to={`instructor/${vinculacionInstructor.instructor.id}`}>{vinculacionInstructor.instructor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vinculacionInstructor.anio1 ? (
                      <Link to={`anio/${vinculacionInstructor.anio1.id}`}>{vinculacionInstructor.anio1.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vinculacionInstructor.vinculacion ? (
                      <Link to={`vinculacion/${vinculacionInstructor.vinculacion.id}`}>
                        {vinculacionInstructor.vinculacion.tipoVinculacion}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vinculacionInstructor.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vinculacionInstructor.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vinculacionInstructor.id}/delete`} color="danger" size="sm">
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
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ vinculacionInstructor }: IRootState) => ({
  vinculacionInstructorList: vinculacionInstructor.entities,
  totalItems: vinculacionInstructor.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VinculacionInstructor);
