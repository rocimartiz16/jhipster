import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IInstructor } from 'app/shared/model/instructor.model';
import { getEntities as getInstructors } from 'app/entities/instructor/instructor.reducer';
import { IAnio } from 'app/shared/model/anio.model';
import { getEntities as getAnios } from 'app/entities/anio/anio.reducer';
import { IVinculacion } from 'app/shared/model/vinculacion.model';
import { getEntities as getVinculacions } from 'app/entities/vinculacion/vinculacion.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vinculacion-instructor.reducer';
import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVinculacionInstructorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVinculacionInstructorUpdateState {
  isNew: boolean;
  instructorId: string;
  anio1Id: string;
  vinculacionId: string;
}

export class VinculacionInstructorUpdate extends React.Component<IVinculacionInstructorUpdateProps, IVinculacionInstructorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      instructorId: '0',
      anio1Id: '0',
      vinculacionId: '0',
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

    this.props.getInstructors();
    this.props.getAnios();
    this.props.getVinculacions();
  }

  saveEntity = (event, errors, values) => {
    values.fechaInicio = convertDateTimeToServer(values.fechaInicio);
    values.fechaFin = convertDateTimeToServer(values.fechaFin);

    if (errors.length === 0) {
      const { vinculacionInstructorEntity } = this.props;
      const entity = {
        ...vinculacionInstructorEntity,
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
    this.props.history.push('/entity/vinculacion-instructor');
  };

  render() {
    const { vinculacionInstructorEntity, instructors, anios, vinculacions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.vinculacionInstructor.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.vinculacionInstructor.home.createOrEditLabel">
                Create or edit a VinculacionInstructor
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vinculacionInstructorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vinculacion-instructor-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fechaInicioLabel" for="fechaInicio">
                    <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaInicio">Fecha Inicio</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-instructor-fechaInicio"
                    type="datetime-local"
                    className="form-control"
                    name="fechaInicio"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.vinculacionInstructorEntity.fechaInicio)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaFinLabel" for="fechaFin">
                    <Translate contentKey="ghostceetApp.vinculacionInstructor.fechaFin">Fecha Fin</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-instructor-fechaFin"
                    type="datetime-local"
                    className="form-control"
                    name="fechaFin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.vinculacionInstructorEntity.fechaFin)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="instructor.id">
                    <Translate contentKey="ghostceetApp.vinculacionInstructor.instructor">Instructor</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-instructor-instructor"
                    type="select"
                    className="form-control"
                    name="instructor.id"
                    value={isNew ? instructors[0] && instructors[0].id : vinculacionInstructorEntity.instructor.id}
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
                <AvGroup>
                  <Label for="anio1.id">
                    <Translate contentKey="ghostceetApp.vinculacionInstructor.anio1">Anio 1</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-instructor-anio1"
                    type="select"
                    className="form-control"
                    name="anio1.id"
                    value={isNew ? anios[0] && anios[0].id : vinculacionInstructorEntity.anio1.id}
                  >
                    {anios
                      ? anios.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="vinculacion.tipoVinculacion">
                    <Translate contentKey="ghostceetApp.vinculacionInstructor.vinculacion">Vinculacion</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-instructor-vinculacion"
                    type="select"
                    className="form-control"
                    name="vinculacion.id"
                    value={isNew ? vinculacions[0] && vinculacions[0].id : vinculacionInstructorEntity.vinculacion.id}
                  >
                    {vinculacions
                      ? vinculacions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.tipoVinculacion}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vinculacion-instructor" replace color="info">
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
  instructors: storeState.instructor.entities,
  anios: storeState.anio.entities,
  vinculacions: storeState.vinculacion.entities,
  vinculacionInstructorEntity: storeState.vinculacionInstructor.entity,
  loading: storeState.vinculacionInstructor.loading,
  updating: storeState.vinculacionInstructor.updating,
  updateSuccess: storeState.vinculacionInstructor.updateSuccess
});

const mapDispatchToProps = {
  getInstructors,
  getAnios,
  getVinculacions,
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
)(VinculacionInstructorUpdate);
