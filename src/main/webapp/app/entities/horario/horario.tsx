import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './horario.reducer';
import { IHorario } from 'app/shared/model/horario.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHorarioProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Horario extends React.Component<IHorarioProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { horarioList, match } = this.props;
    return (
      <div>
        <h2 id="horario-heading">
          <Translate contentKey="ghostceetApp.horario.home.title">Horarios</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.horario.home.createLabel">Create new Horario</Translate>
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
                  <Translate contentKey="ghostceetApp.horario.horaInicio">Hora Inicio</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.horaFin">Hora Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.modalidad">Modalidad</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.dia">Dia</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.fichaTrimestre">Ficha Trimestre</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.ambiente">Ambiente</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.instructor">Instructor</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.horario.versionHorario">Version Horario</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {horarioList.map((horario, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${horario.id}`} color="link" size="sm">
                      {horario.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={horario.horaInicio} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={horario.horaFin} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{horario.modalidad ? <Link to={`modalidad/${horario.modalidad.id}`}>{horario.modalidad.nombre}</Link> : ''}</td>
                  <td>{horario.dia ? <Link to={`dia/${horario.dia.id}`}>{horario.dia.nombreDia}</Link> : ''}</td>
                  <td>
                    {horario.fichaTrimestre ? (
                      <Link to={`ficha-trimestre/${horario.fichaTrimestre.id}`}>{horario.fichaTrimestre.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{horario.ambiente ? <Link to={`ambiente/${horario.ambiente.id}`}>{horario.ambiente.id}</Link> : ''}</td>
                  <td>{horario.instructor ? <Link to={`instructor/${horario.instructor.id}`}>{horario.instructor.id}</Link> : ''}</td>
                  <td>
                    {horario.versionHorario ? (
                      <Link to={`version-horario/${horario.versionHorario.id}`}>{horario.versionHorario.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${horario.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horario.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horario.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ horario }: IRootState) => ({
  horarioList: horario.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Horario);
