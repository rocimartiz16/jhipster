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
import { IEstadoFicha } from 'app/shared/model/estado-ficha.model';
import { getEntities as getEstadoFichas } from 'app/entities/estado-ficha/estado-ficha.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ficha.reducer';
import { IFicha } from 'app/shared/model/ficha.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFichaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFichaUpdateState {
  isNew: boolean;
  programaId: string;
  estadoFichaId: string;
}

export class FichaUpdate extends React.Component<IFichaUpdateProps, IFichaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      programaId: '0',
      estadoFichaId: '0',
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
    this.props.getEstadoFichas();
  }

  saveEntity = (event, errors, values) => {
    values.fechaInicio = convertDateTimeToServer(values.fechaInicio);
    values.fechaFin = convertDateTimeToServer(values.fechaFin);

    if (errors.length === 0) {
      const { fichaEntity } = this.props;
      const entity = {
        ...fichaEntity,
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
    this.props.history.push('/entity/ficha');
  };

  render() {
    const { fichaEntity, programas, estadoFichas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.ficha.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.ficha.home.createOrEditLabel">Create or edit a Ficha</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fichaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ficha-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="numeroLabel" for="numero">
                    <Translate contentKey="ghostceetApp.ficha.numero">Numero</Translate>
                  </Label>
                  <AvField
                    id="ficha-numero"
                    type="text"
                    name="numero"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaInicioLabel" for="fechaInicio">
                    <Translate contentKey="ghostceetApp.ficha.fechaInicio">Fecha Inicio</Translate>
                  </Label>
                  <AvInput
                    id="ficha-fechaInicio"
                    type="datetime-local"
                    className="form-control"
                    name="fechaInicio"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.fichaEntity.fechaInicio)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaFinLabel" for="fechaFin">
                    <Translate contentKey="ghostceetApp.ficha.fechaFin">Fecha Fin</Translate>
                  </Label>
                  <AvInput
                    id="ficha-fechaFin"
                    type="datetime-local"
                    className="form-control"
                    name="fechaFin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.fichaEntity.fechaFin)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="rutaLabel" for="ruta">
                    <Translate contentKey="ghostceetApp.ficha.ruta">Ruta</Translate>
                  </Label>
                  <AvField
                    id="ficha-ruta"
                    type="text"
                    name="ruta"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.ficha.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="ficha-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && fichaEntity.estado) || 'Habilitado'}
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
                  <Label for="programa.id">
                    <Translate contentKey="ghostceetApp.ficha.programa">Programa</Translate>
                  </Label>
                  <AvInput
                    id="ficha-programa"
                    type="select"
                    className="form-control"
                    name="programa.id"
                    value={isNew ? programas[0] && programas[0].id : fichaEntity.programa.id}
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
                <AvGroup>
                  <Label for="estadoFicha.nombreEstado">
                    <Translate contentKey="ghostceetApp.ficha.estadoFicha">Estado Ficha</Translate>
                  </Label>
                  <AvInput
                    id="ficha-estadoFicha"
                    type="select"
                    className="form-control"
                    name="estadoFicha.id"
                    value={isNew ? estadoFichas[0] && estadoFichas[0].id : fichaEntity.estadoFicha.id}
                  >
                    {estadoFichas
                      ? estadoFichas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombreEstado}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ficha" replace color="info">
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
  estadoFichas: storeState.estadoFicha.entities,
  fichaEntity: storeState.ficha.entity,
  loading: storeState.ficha.loading,
  updating: storeState.ficha.updating,
  updateSuccess: storeState.ficha.updateSuccess
});

const mapDispatchToProps = {
  getProgramas,
  getEstadoFichas,
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
)(FichaUpdate);
