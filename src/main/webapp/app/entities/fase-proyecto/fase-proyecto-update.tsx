import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProyecto } from 'app/shared/model/proyecto.model';
import { getEntities as getProyectos } from 'app/entities/proyecto/proyecto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './fase-proyecto.reducer';
import { IFaseProyecto } from 'app/shared/model/fase-proyecto.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFaseProyectoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFaseProyectoUpdateState {
  isNew: boolean;
  proyectoId: string;
}

export class FaseProyectoUpdate extends React.Component<IFaseProyectoUpdateProps, IFaseProyectoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      proyectoId: '0',
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

    this.props.getProyectos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { faseProyectoEntity } = this.props;
      const entity = {
        ...faseProyectoEntity,
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
    this.props.history.push('/entity/fase-proyecto');
  };

  render() {
    const { faseProyectoEntity, proyectos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.faseProyecto.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.faseProyecto.home.createOrEditLabel">Create or edit a FaseProyecto</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : faseProyectoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="fase-proyecto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nombreFaseLabel" for="nombreFase">
                    <Translate contentKey="ghostceetApp.faseProyecto.nombreFase">Nombre Fase</Translate>
                  </Label>
                  <AvField
                    id="fase-proyecto-nombreFase"
                    type="text"
                    name="nombreFase"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 20, errorMessage: translate('entity.validation.maxlength', { max: 20 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoFaseLabel" for="estadoFase">
                    <Translate contentKey="ghostceetApp.faseProyecto.estadoFase">Estado Fase</Translate>
                  </Label>
                  <AvField
                    id="fase-proyecto-estadoFase"
                    type="text"
                    name="estadoFase"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 40, errorMessage: translate('entity.validation.maxlength', { max: 40 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="proyecto.codigo">
                    <Translate contentKey="ghostceetApp.faseProyecto.proyecto">Proyecto</Translate>
                  </Label>
                  <AvInput
                    id="fase-proyecto-proyecto"
                    type="select"
                    className="form-control"
                    name="proyecto.id"
                    value={isNew ? proyectos[0] && proyectos[0].id : faseProyectoEntity.proyecto.id}
                  >
                    {proyectos
                      ? proyectos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.codigo}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/fase-proyecto" replace color="info">
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
  proyectos: storeState.proyecto.entities,
  faseProyectoEntity: storeState.faseProyecto.entity,
  loading: storeState.faseProyecto.loading,
  updating: storeState.faseProyecto.updating,
  updateSuccess: storeState.faseProyecto.updateSuccess
});

const mapDispatchToProps = {
  getProyectos,
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
)(FaseProyectoUpdate);
