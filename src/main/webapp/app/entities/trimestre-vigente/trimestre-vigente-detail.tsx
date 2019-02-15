import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './trimestre-vigente.reducer';
import { ITrimestreVigente } from 'app/shared/model/trimestre-vigente.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITrimestreVigenteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TrimestreVigenteDetail extends React.Component<ITrimestreVigenteDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { trimestreVigenteEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.trimestreVigente.detail.title">TrimestreVigente</Translate> [
            <b>{trimestreVigenteEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="anio">
                <Translate contentKey="ghostceetApp.trimestreVigente.anio">Anio</Translate>
              </span>
            </dt>
            <dd>{trimestreVigenteEntity.anio}</dd>
            <dt>
              <span id="trimestreProgramado">
                <Translate contentKey="ghostceetApp.trimestreVigente.trimestreProgramado">Trimestre Programado</Translate>
              </span>
            </dt>
            <dd>{trimestreVigenteEntity.trimestreProgramado}</dd>
            <dt>
              <span id="fechaInicio">
                <Translate contentKey="ghostceetApp.trimestreVigente.fechaInicio">Fecha Inicio</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={trimestreVigenteEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="fechaFin">
                <Translate contentKey="ghostceetApp.trimestreVigente.fechaFin">Fecha Fin</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={trimestreVigenteEntity.fechaFin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.trimestreVigente.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{trimestreVigenteEntity.estado}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.trimestreVigente.anio1">Anio 1</Translate>
            </dt>
            <dd>{trimestreVigenteEntity.anio1 ? trimestreVigenteEntity.anio1.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/trimestre-vigente" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/trimestre-vigente/${trimestreVigenteEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ trimestreVigente }: IRootState) => ({
  trimestreVigenteEntity: trimestreVigente.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TrimestreVigenteDetail);
