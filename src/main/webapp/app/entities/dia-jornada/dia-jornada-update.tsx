import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
import { getEntities as getJornadaInstructors } from 'app/entities/jornada-instructor/jornada-instructor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './dia-jornada.reducer';
import { IDiaJornada } from 'app/shared/model/dia-jornada.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDiaJornadaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDiaJornadaUpdateState {
  isNew: boolean;
  jornadaInstructorId: string;
}

export class DiaJornadaUpdate extends React.Component<IDiaJornadaUpdateProps, IDiaJornadaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      jornadaInstructorId: '0',
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

    this.props.getJornadaInstructors();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { diaJornadaEntity } = this.props;
      const entity = {
        ...diaJornadaEntity,
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
    this.props.history.push('/entity/dia-jornada');
  };

  render() {
    const { diaJornadaEntity, jornadaInstructors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.diaJornada.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.diaJornada.home.createOrEditLabel">Create or edit a DiaJornada</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : diaJornadaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="dia-jornada-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="horaInicioLabel" for="horaInicio">
                    <Translate contentKey="ghostceetApp.diaJornada.horaInicio">Hora Inicio</Translate>
                  </Label>
                  <AvField
                    id="dia-jornada-horaInicio"
                    type="string"
                    className="form-control"
                    name="horaInicio"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horaFinLabel" for="horaFin">
                    <Translate contentKey="ghostceetApp.diaJornada.horaFin">Hora Fin</Translate>
                  </Label>
                  <AvField
                    id="dia-jornada-horaFin"
                    type="string"
                    className="form-control"
                    name="horaFin"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="jornadaInstructor.nombreJornada">
                    <Translate contentKey="ghostceetApp.diaJornada.jornadaInstructor">Jornada Instructor</Translate>
                  </Label>
                  <AvInput
                    id="dia-jornada-jornadaInstructor"
                    type="select"
                    className="form-control"
                    name="jornadaInstructor.id"
                    value={isNew ? jornadaInstructors[0] && jornadaInstructors[0].id : diaJornadaEntity.jornadaInstructor.id}
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
                <Button tag={Link} id="cancel-save" to="/entity/dia-jornada" replace color="info">
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
  jornadaInstructors: storeState.jornadaInstructor.entities,
  diaJornadaEntity: storeState.diaJornada.entity,
  loading: storeState.diaJornada.loading,
  updating: storeState.diaJornada.updating,
  updateSuccess: storeState.diaJornada.updateSuccess
});

const mapDispatchToProps = {
  getJornadaInstructors,
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
)(DiaJornadaUpdate);
