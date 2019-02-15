import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
import { getEntities as getVinculacionInstructors } from 'app/entities/vinculacion-instructor/vinculacion-instructor.reducer';
import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
import { getEntities as getJornadaInstructors } from 'app/entities/jornada-instructor/jornada-instructor.reducer';
import { IInstructor } from 'app/shared/model/instructor.model';
import { getEntities as getInstructors } from 'app/entities/instructor/instructor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disponibilidad-horaria.reducer';
import { IDisponibilidadHoraria } from 'app/shared/model/disponibilidad-horaria.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDisponibilidadHorariaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDisponibilidadHorariaUpdateState {
  isNew: boolean;
  vinculacionInstructorId: string;
  jornadaId: string;
  instructorId: string;
}

export class DisponibilidadHorariaUpdate extends React.Component<IDisponibilidadHorariaUpdateProps, IDisponibilidadHorariaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vinculacionInstructorId: '0',
      jornadaId: '0',
      instructorId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVinculacionInstructors();
    this.props.getJornadaInstructors();
    this.props.getInstructors();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { disponibilidadHorariaEntity } = this.props;
      const entity = {
        ...disponibilidadHorariaEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/disponibilidad-horaria');
  };

  render() {
    const { disponibilidadHorariaEntity, vinculacionInstructors, jornadaInstructors, instructors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.disponibilidadHoraria.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.disponibilidadHoraria.home.createOrEditLabel">
                Create or edit a DisponibilidadHoraria
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : disponibilidadHorariaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="disponibilidad-horaria-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="vinculacionInstructor.id">
                    <Translate contentKey="ghostceetApp.disponibilidadHoraria.vinculacionInstructor">Vinculacion Instructor</Translate>
                  </Label>
                  <AvInput
                    id="disponibilidad-horaria-vinculacionInstructor"
                    type="select"
                    className="form-control"
                    name="vinculacionInstructor.id"
                    value={
                      isNew
                        ? vinculacionInstructors[0] && vinculacionInstructors[0].id
                        : disponibilidadHorariaEntity.vinculacionInstructor.id
                    }
                  >
                    {vinculacionInstructors
                      ? vinculacionInstructors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="jornada.nombreJornada">
                    <Translate contentKey="ghostceetApp.disponibilidadHoraria.jornada">Jornada</Translate>
                  </Label>
                  <AvInput
                    id="disponibilidad-horaria-jornada"
                    type="select"
                    className="form-control"
                    name="jornada.id"
                    value={isNew ? jornadaInstructors[0] && jornadaInstructors[0].id : disponibilidadHorariaEntity.jornada.id}
                  >
                    {jornadaInstructors
                      ? jornadaInstructors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombreJornada}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="instructor.id">
                    <Translate contentKey="ghostceetApp.disponibilidadHoraria.instructor">Instructor</Translate>
                  </Label>
                  <AvInput
                    id="disponibilidad-horaria-instructor"
                    type="select"
                    className="form-control"
                    name="instructor.id"
                    value={isNew ? instructors[0] && instructors[0].id : disponibilidadHorariaEntity.instructor.id}
                  >
                    {instructors
                      ? instructors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/disponibilidad-horaria" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  vinculacionInstructors: storeState.vinculacionInstructor.entities,
  jornadaInstructors: storeState.jornadaInstructor.entities,
  instructors: storeState.instructor.entities,
  disponibilidadHorariaEntity: storeState.disponibilidadHoraria.entity,
  loading: storeState.disponibilidadHoraria.loading,
  updating: storeState.disponibilidadHoraria.updating,
  updateSuccess: storeState.disponibilidadHoraria.updateSuccess
});

const mapDispatchToProps = {
  getVinculacionInstructors,
  getJornadaInstructors,
  getInstructors,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DisponibilidadHorariaUpdate);
