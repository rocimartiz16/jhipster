import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './jornada-instructor.reducer';
import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IJornadaInstructorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IJornadaInstructorUpdateState {
  isNew: boolean;
}

export class JornadaInstructorUpdate extends React.Component<IJornadaInstructorUpdateProps, IJornadaInstructorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { jornadaInstructorEntity } = this.props;
      const entity = {
        ...jornadaInstructorEntity,
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
    this.props.history.push('/entity/jornada-instructor');
  };

  render() {
    const { jornadaInstructorEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.jornadaInstructor.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.jornadaInstructor.home.createOrEditLabel">Create or edit a JornadaInstructor</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : jornadaInstructorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="jornada-instructor-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nombreJornadaLabel" for="nombreJornada">
                    <Translate contentKey="ghostceetApp.jornadaInstructor.nombreJornada">Nombre Jornada</Translate>
                  </Label>
                  <AvField
                    id="jornada-instructor-nombreJornada"
                    type="text"
                    name="nombreJornada"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 80, errorMessage: translate('entity.validation.maxlength', { max: 80 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descripcionLabel" for="descripcion">
                    <Translate contentKey="ghostceetApp.jornadaInstructor.descripcion">Descripcion</Translate>
                  </Label>
                  <AvField
                    id="jornada-instructor-descripcion"
                    type="text"
                    name="descripcion"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 200, errorMessage: translate('entity.validation.maxlength', { max: 200 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.jornadaInstructor.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="jornada-instructor-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && jornadaInstructorEntity.estado) || 'Habilitado'}
                  >
                    <option value="Habilitado">
                      <Translate contentKey="ghostceetApp.Estado.Habilitado" />
                    </option>
                    <option value="Inhabilitado">
                      <Translate contentKey="ghostceetApp.Estado.Inhabilitado" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/jornada-instructor" replace color="info">
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
  jornadaInstructorEntity: storeState.jornadaInstructor.entity,
  loading: storeState.jornadaInstructor.loading,
  updating: storeState.jornadaInstructor.updating,
  updateSuccess: storeState.jornadaInstructor.updateSuccess
});

const mapDispatchToProps = {
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
)(JornadaInstructorUpdate);
