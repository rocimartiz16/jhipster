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
import { getEntities } from './trimestre-vigente.reducer';
import { ITrimestreVigente } from 'app/shared/model/trimestre-vigente.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITrimestreVigenteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITrimestreVigenteState = IPaginationBaseState;

export class TrimestreVigente extends React.Component<ITrimestreVigenteProps, ITrimestreVigenteState> {
  state: ITrimestreVigenteState = {
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
    const { trimestreVigenteList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="trimestre-vigente-heading">
          <Translate contentKey="ghostceetApp.trimestreVigente.home.title">Trimestre Vigentes</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.trimestreVigente.home.createLabel">Create new Trimestre Vigente</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('anio')}>
                  <Translate contentKey="ghostceetApp.trimestreVigente.anio">Anio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('trimestreProgramado')}>
                  <Translate contentKey="ghostceetApp.trimestreVigente.trimestreProgramado">Trimestre Programado</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fechaInicio')}>
                  <Translate contentKey="ghostceetApp.trimestreVigente.fechaInicio">Fecha Inicio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fechaFin')}>
                  <Translate contentKey="ghostceetApp.trimestreVigente.fechaFin">Fecha Fin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('estado')}>
                  <Translate contentKey="ghostceetApp.trimestreVigente.estado">Estado</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.trimestreVigente.anio1">Anio 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trimestreVigenteList.map((trimestreVigente, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${trimestreVigente.id}`} color="link" size="sm">
                      {trimestreVigente.id}
                    </Button>
                  </td>
                  <td>{trimestreVigente.anio}</td>
                  <td>{trimestreVigente.trimestreProgramado}</td>
                  <td>
                    <TextFormat type="date" value={trimestreVigente.fechaInicio} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={trimestreVigente.fechaFin} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <Translate contentKey={`ghostceetApp.Estado.${trimestreVigente.estado}`} />
                  </td>
                  <td>{trimestreVigente.anio1 ? <Link to={`anio/${trimestreVigente.anio1.id}`}>{trimestreVigente.anio1.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${trimestreVigente.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trimestreVigente.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trimestreVigente.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ trimestreVigente }: IRootState) => ({
  trimestreVigenteList: trimestreVigente.entities,
  totalItems: trimestreVigente.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TrimestreVigente);
