import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IModalidad } from 'app/shared/model/modalidad.model';
import { getEntities as getModalidads } from 'app/entities/modalidad/modalidad.reducer';
import { IDia } from 'app/shared/model/dia.model';
import { getEntities as getDias } from 'app/entities/dia/dia.reducer';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';
import { getEntities as getFichaTrimestres } from 'app/entities/ficha-trimestre/ficha-trimestre.reducer';
import { IAmbiente } from 'app/shared/model/ambiente.model';
import { getEntities as getAmbientes } from 'app/entities/ambiente/ambiente.reducer';
import { IInstructor } from 'app/shared/model/instructor.model';
import { getEntities as getInstructors } from 'app/entities/instructor/instructor.reducer';
import { IVersionHorario } from 'app/shared/model/version-horario.model';
import { getEntities as getVersionHorarios } from 'app/entities/version-horario/version-horario.reducer';
import { getEntity, updateEntity, createEntity, reset } from './horario.reducer';
import { IHorario } from 'app/shared/model/horario.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHorarioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IHorarioUpdateState {
  isNew: boolean;
  modalidadId: string;
  diaId: string;
  fichaTrimestreId: string;
  ambienteId: string;
  instructorId: string;
  versionHorarioId: string;
}

export class HorarioUpdate extends React.Component<IHorarioUpdateProps, IHorarioUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      modalidadId: '0',
      diaId: '0',
      fichaTrimestreId: '0',
      ambienteId: '0',
      instructorId: '0',
      versionHorarioId: '0',
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

    this.props.getModalidads();
    this.props.getDias();
    this.props.getFichaTrimestres();
    this.props.getAmbientes();
    this.props.getInstructors();
    this.props.getVersionHorarios();
  }

  saveEntity = (event, errors, values) => {
    values.horaInicio = convertDateTimeToServer(values.horaInicio);
    values.horaFin = convertDateTimeToServer(values.horaFin);

    if (errors.length === 0) {
      const { horarioEntity } = this.props;
      const entity = {
        ...horarioEntity,
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
    this.props.history.push('/entity/horario');
  };

  render() {
    const { horarioEntity, modalidads, dias, fichaTrimestres, ambientes, instructors, versionHorarios, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.horario.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.horario.home.createOrEditLabel">Create or edit a Horario</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : horarioEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="horario-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="horaInicioLabel" for="horaInicio">
                    <Translate contentKey="ghostceetApp.horario.horaInicio">Hora Inicio</Translate>
                  </Label>
                  <AvInput
                    id="horario-horaInicio"
                    type="datetime-local"
                    className="form-control"
                    name="horaInicio"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.horarioEntity.horaInicio)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horaFinLabel" for="horaFin">
                    <Translate contentKey="ghostceetApp.horario.horaFin">Hora Fin</Translate>
                  </Label>
                  <AvInput
                    id="horario-horaFin"
                    type="datetime-local"
                    className="form-control"
                    name="horaFin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.horarioEntity.horaFin)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="modalidad.nombre">
                    <Translate contentKey="ghostceetApp.horario.modalidad">Modalidad</Translate>
                  </Label>
                  <AvInput
                    id="horario-modalidad"
                    type="select"
                    className="form-control"
                    name="modalidad.id"
                    value={isNew ? modalidads[0] && modalidads[0].id : horarioEntity.modalidad.id}
                  >
                    {modalidads
                      ? modalidads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombre}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="dia.nombreDia">
                    <Translate contentKey="ghostceetApp.horario.dia">Dia</Translate>
                  </Label>
                  <AvInput
                    id="horario-dia"
                    type="select"
                    className="form-control"
                    name="dia.id"
                    value={isNew ? dias[0] && dias[0].id : horarioEntity.dia.id}
                  >
                    {dias
                      ? dias.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombreDia}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="fichaTrimestre.id">
                    <Translate contentKey="ghostceetApp.horario.fichaTrimestre">Ficha Trimestre</Translate>
                  </Label>
                  <AvInput
                    id="horario-fichaTrimestre"
                    type="select"
                    className="form-control"
                    name="fichaTrimestre.id"
                    value={isNew ? fichaTrimestres[0] && fichaTrimestres[0].id : horarioEntity.fichaTrimestre.id}
                  >
                    {fichaTrimestres
                      ? fichaTrimestres.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ambiente.id">
                    <Translate contentKey="ghostceetApp.horario.ambiente">Ambiente</Translate>
                  </Label>
                  <AvInput
                    id="horario-ambiente"
                    type="select"
                    className="form-control"
                    name="ambiente.id"
                    value={isNew ? ambientes[0] && ambientes[0].id : horarioEntity.ambiente.id}
                  >
                    {ambientes
                      ? ambientes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="instructor.id">
                    <Translate contentKey="ghostceetApp.horario.instructor">Instructor</Translate>
                  </Label>
                  <AvInput
                    id="horario-instructor"
                    type="select"
                    className="form-control"
                    name="instructor.id"
                    value={isNew ? instructors[0] && instructors[0].id : horarioEntity.instructor.id}
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
                  <Label for="versionHorario.id">
                    <Translate contentKey="ghostceetApp.horario.versionHorario">Version Horario</Translate>
                  </Label>
                  <AvInput
                    id="horario-versionHorario"
                    type="select"
                    className="form-control"
                    name="versionHorario.id"
                    value={isNew ? versionHorarios[0] && versionHorarios[0].id : horarioEntity.versionHorario.id}
                  >
                    {versionHorarios
                      ? versionHorarios.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/horario" replace color="info">
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
  modalidads: storeState.modalidad.entities,
  dias: storeState.dia.entities,
  fichaTrimestres: storeState.fichaTrimestre.entities,
  ambientes: storeState.ambiente.entities,
  instructors: storeState.instructor.entities,
  versionHorarios: storeState.versionHorario.entities,
  horarioEntity: storeState.horario.entity,
  loading: storeState.horario.loading,
  updating: storeState.horario.updating,
  updateSuccess: storeState.horario.updateSuccess
});

const mapDispatchToProps = {
  getModalidads,
  getDias,
  getFichaTrimestres,
  getAmbientes,
  getInstructors,
  getVersionHorarios,
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
)(HorarioUpdate);
