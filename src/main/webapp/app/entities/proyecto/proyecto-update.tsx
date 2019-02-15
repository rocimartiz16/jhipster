import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPrograma } from 'app/shared/model/programa.model';
import { getEntities as getProgramas } from 'app/entities/programa/programa.reducer';
import { getEntity, updateEntity, createEntity, reset } from './proyecto.reducer';
import { IProyecto } from 'app/shared/model/proyecto.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProyectoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProyectoUpdateState {
  isNew: boolean;
  programaId: string;
}

export class ProyectoUpdate extends React.Component<IProyectoUpdateProps, IProyectoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      programaId: '0',
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

    this.props.getProgramas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { proyectoEntity } = this.props;
      const entity = {
        ...proyectoEntity,
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
    this.props.history.push('/entity/proyecto');
  };

  render() {
    const { proyectoEntity, programas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.proyecto.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.proyecto.home.createOrEditLabel">Create or edit a Proyecto</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : proyectoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="proyecto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codigoLabel" for="codigo">
                    <Translate contentKey="ghostceetApp.proyecto.codigo">Codigo</Translate>
                  </Label>
                  <AvField
                    id="proyecto-codigo"
                    type="text"
                    name="codigo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.proyecto.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="proyecto-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && proyectoEntity.estado) || 'Habilitado'}
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
                  <Label id="nombreProyectoLabel" for="nombreProyecto">
                    <Translate contentKey="ghostceetApp.proyecto.nombreProyecto">Nombre Proyecto</Translate>
                  </Label>
                  <AvField
                    id="proyecto-nombreProyecto"
                    type="text"
                    name="nombreProyecto"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 300, errorMessage: translate('entity.validation.maxlength', { max: 300 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="programa.id">
                    <Translate contentKey="ghostceetApp.proyecto.programa">Programa</Translate>
                  </Label>
                  <AvInput
                    id="proyecto-programa"
                    type="select"
                    className="form-control"
                    name="programa.id"
                    value={isNew ? programas[0] && programas[0].id : proyectoEntity.programa.id}
                  >
                    {programas
                      ? programas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/proyecto" replace color="info">
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
  programas: storeState.programa.entities,
  proyectoEntity: storeState.proyecto.entity,
  loading: storeState.proyecto.loading,
  updating: storeState.proyecto.updating,
  updateSuccess: storeState.proyecto.updateSuccess
});

const mapDispatchToProps = {
  getProgramas,
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
)(ProyectoUpdate);
