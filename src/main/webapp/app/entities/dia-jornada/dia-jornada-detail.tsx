import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dia-jornada.reducer';
import { IDiaJornada } from 'app/shared/model/dia-jornada.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDiaJornadaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DiaJornadaDetail extends React.Component<IDiaJornadaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { diaJornadaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.diaJornada.detail.title">DiaJornada</Translate> [<b>{diaJornadaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="horaInicio">
                <Translate contentKey="ghostceetApp.diaJornada.horaInicio">Hora Inicio</Translate>
              </span>
            </dt>
            <dd>{diaJornadaEntity.horaInicio}</dd>
            <dt>
              <span id="horaFin">
                <Translate contentKey="ghostceetApp.diaJornada.horaFin">Hora Fin</Translate>
              </span>
            </dt>
            <dd>{diaJornadaEntity.horaFin}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.diaJornada.jornadaInstructor">Jornada Instructor</Translate>
            </dt>
            <dd>{diaJornadaEntity.jornadaInstructor ? diaJornadaEntity.jornadaInstructor.nombreJornada : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/dia-jornada" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/dia-jornada/${diaJornadaEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ diaJornada }: IRootState) => ({
  diaJornadaEntity: diaJornada.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DiaJornadaDetail);
