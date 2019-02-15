import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './actividad-proyecto.reducer';
import { IActividadProyecto } from 'app/shared/model/actividad-proyecto.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IActividadProyectoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ActividadProyectoDetail extends React.Component<IActividadProyectoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { actividadProyectoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.actividadProyecto.detail.title">ActividadProyecto</Translate> [
            <b>{actividadProyectoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="numeroActividad">
                <Translate contentKey="ghostceetApp.actividadProyecto.numeroActividad">Numero Actividad</Translate>
              </span>
            </dt>
            <dd>{actividadProyectoEntity.numeroActividad}</dd>
            <dt>
              <span id="nombreActividad">
                <Translate contentKey="ghostceetApp.actividadProyecto.nombreActividad">Nombre Actividad</Translate>
              </span>
            </dt>
            <dd>{actividadProyectoEntity.nombreActividad}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.actividadProyecto.faseProyecto">Fase Proyecto</Translate>
            </dt>
            <dd>{actividadProyectoEntity.faseProyecto ? actividadProyectoEntity.faseProyecto.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/actividad-proyecto" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/actividad-proyecto/${actividadProyectoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ actividadProyecto }: IRootState) => ({
  actividadProyectoEntity: actividadProyecto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActividadProyectoDetail);
