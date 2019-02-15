import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArea } from 'app/shared/model/area.model';
import { getEntities as getAreas } from 'app/entities/area/area.reducer';
import { IInstructor } from 'app/shared/model/instructor.model';
import { getEntities as getInstructors } from 'app/entities/instructor/instructor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './area-instructor.reducer';
import { IAreaInstructor } from 'app/shared/model/area-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAreaInstructorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAreaInstructorUpdateState {
  isNew: boolean;
  areaId: string;
  instructorId: string;
}

export class AreaInstructorUpdate extends React.Component<IAreaInstructorUpdateProps, IAreaInstructorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      areaId: '0',
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

    this.props.getAreas();
    this.props.getInstructors();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { areaInstructorEntity } = this.props;
      const entity = {
        ...areaInstructorEntity,
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
    this.props.history.push('/entity/area-instructor');
  };

  render() {
    const { areaInstructorEntity, areas, instructors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.areaInstructor.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.areaInstructor.home.createOrEditLabel">Create or edit a AreaInstructor</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : areaInstructorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="area-instructor-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.areaInstructor.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="area-instructor-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && areaInstructorEntity.estado) || 'Habilitado'}
                  >
                    <option value="Habilitado">
                      <Translate contentKey="ghostceetApp.Estado.Habilitado" />
                    </option>
                    <option value="Inhabilitado">
                      <Translate contentKey="ghostceetApp.Estado.Inhabilitado" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="area.nombreArea">
                    <Translate contentKey="ghostceetApp.areaInstructor.area">Area</Translate>
                  </Label>
                  <AvInput
                    id="area-instructor-area"
                    type="select"
                    className="form-control"
                    name="area.id"
                    value={isNew ? areas[0] && areas[0].id : areaInstructorEntity.area.id}
                  >
                    {areas
                      ? areas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombreArea}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="instructor.id">
                    <Translate contentKey="ghostceetApp.areaInstructor.instructor">Instructor</Translate>
                  </Label>
                  <AvInput
                    id="area-instructor-instructor"
                    type="select"
                    className="form-control"
                    name="instructor.id"
                    value={isNew ? instructors[0] && instructors[0].id : areaInstructorEntity.instructor.id}
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
                <Button tag={Link} id="cancel-save" to="/entity/area-instructor" replace color="info">
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
  areas: storeState.area.entities,
  instructors: storeState.instructor.entities,
  areaInstructorEntity: storeState.areaInstructor.entity,
  loading: storeState.areaInstructor.loading,
  updating: storeState.areaInstructor.updating,
  updateSuccess: storeState.areaInstructor.updateSuccess
});

const mapDispatchToProps = {
  getAreas,
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
)(AreaInstructorUpdate);
