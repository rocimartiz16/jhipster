import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './jornada-instructor.reducer';
import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IJornadaInstructorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class JornadaInstructorDetail extends React.Component<IJornadaInstructorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { jornadaInstructorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.jornadaInstructor.detail.title">JornadaInstructor</Translate> [
            <b>{jornadaInstructorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombreJornada">
                <Translate contentKey="ghostceetApp.jornadaInstructor.nombreJornada">Nombre Jornada</Translate>
              </span>
            </dt>
            <dd>{jornadaInstructorEntity.nombreJornada}</dd>
            <dt>
              <span id="descripcion">
                <Translate contentKey="ghostceetApp.jornadaInstructor.descripcion">Descripcion</Translate>
              </span>
            </dt>
            <dd>{jornadaInstructorEntity.descripcion}</dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.jornadaInstructor.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{jornadaInstructorEntity.estado}</dd>
          </dl>
          <Button tag={Link} to="/entity/jornada-instructor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/jornada-instructor/${jornadaInstructorEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ jornadaInstructor }: IRootState) => ({
  jornadaInstructorEntity: jornadaInstructor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(JornadaInstructorDetail);
