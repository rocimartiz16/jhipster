import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ficha.reducer';
import { IFicha } from 'app/shared/model/ficha.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFichaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FichaDetail extends React.Component<IFichaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fichaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.ficha.detail.title">Ficha</Translate> [<b>{fichaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="numero">
                <Translate contentKey="ghostceetApp.ficha.numero">Numero</Translate>
              </span>
            </dt>
            <dd>{fichaEntity.numero}</dd>
            <dt>
              <span id="fechaInicio">
                <Translate contentKey="ghostceetApp.ficha.fechaInicio">Fecha Inicio</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fichaEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="fechaFin">
                <Translate contentKey="ghostceetApp.ficha.fechaFin">Fecha Fin</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fichaEntity.fechaFin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="ruta">
                <Translate contentKey="ghostceetApp.ficha.ruta">Ruta</Translate>
              </span>
            </dt>
            <dd>{fichaEntity.ruta}</dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.ficha.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{fichaEntity.estado}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.ficha.programa">Programa</Translate>
            </dt>
            <dd>{fichaEntity.programa ? fichaEntity.programa.id : ''}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.ficha.estadoFicha">Estado Ficha</Translate>
            </dt>
            <dd>{fichaEntity.estadoFicha ? fichaEntity.estadoFicha.nombreEstado : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ficha" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ficha/${fichaEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ficha }: IRootState) => ({
  fichaEntity: ficha.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FichaDetail);
