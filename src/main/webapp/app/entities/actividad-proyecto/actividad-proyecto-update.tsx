import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFaseProyecto } from 'app/shared/model/fase-proyecto.model';
import { getEntities as getFaseProyectos } from 'app/entities/fase-proyecto/fase-proyecto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './actividad-proyecto.reducer';
import { IActividadProyecto } from 'app/shared/model/actividad-proyecto.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IActividadProyectoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IActividadProyectoUpdateState {
  isNew: boolean;
  faseProyectoId: string;
}

export class ActividadProyectoUpdate extends React.Component<IActividadProyectoUpdateProps, IActividadProyectoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      faseProyectoId: '0',
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

    this.props.getFaseProyectos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { actividadProyectoEntity } = this.props;
      const entity = {
        ...actividadProyectoEntity,
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
    this.props.history.push('/entity/actividad-proyecto');
  };

  render() {
    const { actividadProyectoEntity, faseProyectos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.actividadProyecto.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.actividadProyecto.home.createOrEditLabel">Create or edit a ActividadProyecto</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : actividadProyectoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="actividad-proyecto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="numeroActividadLabel" for="numeroActividad">
                    <Translate contentKey="ghostceetApp.actividadProyecto.numeroActividad">Numero Actividad</Translate>
                  </Label>
                  <AvField
                    id="actividad-proyecto-numeroActividad"
                    type="string"
                    className="form-control"
                    name="numeroActividad"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nombreActividadLabel" for="nombreActividad">
                    <Translate contentKey="ghostceetApp.actividadProyecto.nombreActividad">Nombre Actividad</Translate>
                  </Label>
                  <AvField
                    id="actividad-proyecto-nombreActividad"
                    type="text"
                    name="nombreActividad"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 300, errorMessage: translate('entity.validation.maxlength', { max: 300 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="faseProyecto.id">
                    <Translate contentKey="ghostceetApp.actividadProyecto.faseProyecto">Fase Proyecto</Translate>
                  </Label>
                  <AvInput
                    id="actividad-proyecto-faseProyecto"
                    type="select"
                    className="form-control"
                    name="faseProyecto.id"
                    value={isNew ? faseProyectos[0] && faseProyectos[0].id : actividadProyectoEntity.faseProyecto.id}
                  >
                    {faseProyectos
                      ? faseProyectos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/actividad-proyecto" replace color="info">
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
  faseProyectos: storeState.faseProyecto.entities,
  actividadProyectoEntity: storeState.actividadProyecto.entity,
  loading: storeState.actividadProyecto.loading,
  updating: storeState.actividadProyecto.updating,
  updateSuccess: storeState.actividadProyecto.updateSuccess
});

const mapDispatchToProps = {
  getFaseProyectos,
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
)(ActividadProyectoUpdate);
