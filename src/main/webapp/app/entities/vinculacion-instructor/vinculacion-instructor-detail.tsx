import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vinculacion-instructor.reducer';
import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVinculacionInstructorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VinculacionInstructorDetail extends React.Component<IVinculacionInstructorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vinculacionInstructorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.vinculacionInstructor.detail.title">VinculacionInstructor</Translate> [
            <b>{vinculacionInstructorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fechaInicio">
                <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaInicio">Fecha Inicio</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={vinculacionInstructorEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="fechaFin">
                <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaFin">Fecha Fin</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={vinculacionInstructorEntity.fechaFin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="ghostceetApp.vinculacionInstructor.instructor">Instructor</Translate>
            </dt>
            <dd>{vinculacionInstructorEntity.instructor ? vinculacionInstructorEntity.instructor.id : ''}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.vinculacionInstructor.anio1">Anio 1</Translate>
            </dt>
            <dd>{vinculacionInstructorEntity.anio1 ? vinculacionInstructorEntity.anio1.id : ''}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.vinculacionInstructor.vinculacion">Vinculacion</Translate>
            </dt>
            <dd>{vinculacionInstructorEntity.vinculacion ? vinculacionInstructorEntity.vinculacion.tipoVinculacion : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vinculacion-instructor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vinculacion-instructor/${vinculacionInstructorEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ vinculacionInstructor }: IRootState) => ({
  vinculacionInstructorEntity: vinculacionInstructor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VinculacionInstructorDetail);
