import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './disponibilidad-horaria.reducer';
import { IDisponibilidadHoraria } from 'app/shared/model/disponibilidad-horaria.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDisponibilidadHorariaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class DisponibilidadHoraria extends React.Component<IDisponibilidadHorariaProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { disponibilidadHorariaList, match } = this.props;
    return (
      <div>
        <h2 id="disponibilidad-horaria-heading">
          <Translate contentKey="ghostceetApp.disponibilidadHoraria.home.title">Disponibilidad Horarias</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ghostceetApp.disponibilidadHoraria.home.createLabel">Create new Disponibilidad Horaria</Translate>
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
                  <Translate contentKey="ghostceetApp.disponibilidadHoraria.vinculacionInstructor">Vinculacion Instructor</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.disponibilidadHoraria.jornada">Jornada</Translate>
                </th>
                <th>
                  <Translate contentKey="ghostceetApp.disponibilidadHoraria.instructor">Instructor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {disponibilidadHorariaList.map((disponibilidadHoraria, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${disponibilidadHoraria.id}`} color="link" size="sm">
                      {disponibilidadHoraria.id}
                    </Button>
                  </td>
                  <td>
                    {disponibilidadHoraria.vinculacionInstructor ? (
                      <Link to={`vinculacion-instructor/${disponibilidadHoraria.vinculacionInstructor.id}`}>
                        {disponibilidadHoraria.vinculacionInstructor.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {disponibilidadHoraria.jornada ? (
                      <Link to={`jornada-instructor/${disponibilidadHoraria.jornada.id}`}>
                        {disponibilidadHoraria.jornada.nombreJornada}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {disponibilidadHoraria.instructor ? (
                      <Link to={`instructor/${disponibilidadHoraria.instructor.id}`}>{disponibilidadHoraria.instructor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${disponibilidadHoraria.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${disponibilidadHoraria.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${disponibilidadHoraria.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ disponibilidadHoraria }: IRootState) => ({
  disponibilidadHorariaList: disponibilidadHoraria.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DisponibilidadHoraria);
