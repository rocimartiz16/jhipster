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
import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
import { getEntities as getVinculacionInstructors } from 'app/entities/vinculacion-instructor/vinculacion-instructor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disponibilidad-competencias.reducer';
import { IDisponibilidadCompetencias } from 'app/shared/model/disponibilidad-competencias.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDisponibilidadCompetenciasUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDisponibilidadCompetenciasUpdateState {
  isNew: boolean;
  instructorId: string;
  vinculacionInstructorId: string;
}

export class DisponibilidadCompetenciasUpdate extends React.Component<
  IDisponibilidadCompetenciasUpdateProps,
  IDisponibilidadCompetenciasUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      instructorId: '0',
      vinculacionInstructorId: '0',
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
    this.props.getVinculacionInstructors();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { disponibilidadCompetenciasEntity } = this.props;
      const entity = {
        ...disponibilidadCompetenciasEntity,
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
    this.props.history.push('/entity/disponibilidad-competencias');
  };

  render() {
    const { disponibilidadCompetenciasEntity, instructors, vinculacionInstructors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.disponibilidadCompetencias.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.disponibilidadCompetencias.home.createOrEditLabel">
                Create or edit a DisponibilidadCompetencias
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : disponibilidadCompetenciasEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="disponibilidad-competencias-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="instructor.id">
                    <Translate contentKey="ghostceetApp.disponibilidadCompetencias.instructor">Instructor</Translate>
                  </Label>
                  <AvInput
                    id="disponibilidad-competencias-instructor"
                    type="select"
                    className="form-control"
                    name="instructor.id"
                    value={isNew ? instructors[0] && instructors[0].id : disponibilidadCompetenciasEntity.instructor.id}
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
                  <Label for="vinculacionInstructor.id">
                    <Translate contentKey="ghostceetApp.disponibilidadCompetencias.vinculacionInstructor">Vinculacion Instructor</Translate>
                  </Label>
                  <AvInput
                    id="disponibilidad-competencias-vinculacionInstructor"
                    type="select"
                    className="form-control"
                    name="vinculacionInstructor.id"
                    value={
                      isNew
                        ? vinculacionInstructors[0] && vinculacionInstructors[0].id
                        : disponibilidadCompetenciasEntity.vinculacionInstructor.id
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
                <Button tag={Link} id="cancel-save" to="/entity/disponibilidad-competencias" replace color="info">
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
  vinculacionInstructors: storeState.vinculacionInstructor.entities,
  disponibilidadCompetenciasEntity: storeState.disponibilidadCompetencias.entity,
  loading: storeState.disponibilidadCompetencias.loading,
  updating: storeState.disponibilidadCompetencias.updating,
  updateSuccess: storeState.disponibilidadCompetencias.updateSuccess
});

const mapDispatchToProps = {
  getInstructors,
  getVinculacionInstructors,
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
)(DisponibilidadCompetenciasUpdate);
