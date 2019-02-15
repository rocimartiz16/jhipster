import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAnio } from 'app/shared/model/anio.model';
import { getEntities as getAnios } from 'app/entities/anio/anio.reducer';
import { getEntity, updateEntity, createEntity, reset } from './trimestre-vigente.reducer';
import { ITrimestreVigente } from 'app/shared/model/trimestre-vigente.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITrimestreVigenteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITrimestreVigenteUpdateState {
  isNew: boolean;
  anio1Id: string;
}

export class TrimestreVigenteUpdate extends React.Component<ITrimestreVigenteUpdateProps, ITrimestreVigenteUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      anio1Id: '0',
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

    this.props.getAnios();
  }

  saveEntity = (event, errors, values) => {
    values.fechaInicio = convertDateTimeToServer(values.fechaInicio);
    values.fechaFin = convertDateTimeToServer(values.fechaFin);

    if (errors.length === 0) {
      const { trimestreVigenteEntity } = this.props;
      const entity = {
        ...trimestreVigenteEntity,
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
    this.props.history.push('/entity/trimestre-vigente');
  };

  render() {
    const { trimestreVigenteEntity, anios, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.trimestreVigente.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.trimestreVigente.home.createOrEditLabel">Create or edit a TrimestreVigente</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : trimestreVigenteEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="trimestre-vigente-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="anioLabel" for="anio">
                    <Translate contentKey="ghostceetApp.trimestreVigente.anio">Anio</Translate>
                  </Label>
                  <AvField
                    id="trimestre-vigente-anio"
                    type="string"
                    className="form-control"
                    name="anio"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="trimestreProgramadoLabel" for="trimestreProgramado">
                    <Translate contentKey="ghostceetApp.trimestreVigente.trimestreProgramado">Trimestre Programado</Translate>
                  </Label>
                  <AvField
                    id="trimestre-vigente-trimestreProgramado"
                    type="string"
                    className="form-control"
                    name="trimestreProgramado"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaInicioLabel" for="fechaInicio">
                    <Translate contentKey="ghostceetApp.trimestreVigente.fechaInicio">Fecha Inicio</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-vigente-fechaInicio"
                    type="datetime-local"
                    className="form-control"
                    name="fechaInicio"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.trimestreVigenteEntity.fechaInicio)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaFinLabel" for="fechaFin">
                    <Translate contentKey="ghostceetApp.trimestreVigente.fechaFin">Fecha Fin</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-vigente-fechaFin"
                    type="datetime-local"
                    className="form-control"
                    name="fechaFin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.trimestreVigenteEntity.fechaFin)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.trimestreVigente.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-vigente-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && trimestreVigenteEntity.estado) || 'Habilitado'}
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
                  <Label for="anio1.id">
                    <Translate contentKey="ghostceetApp.trimestreVigente.anio1">Anio 1</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-vigente-anio1"
                    type="select"
                    className="form-control"
                    name="anio1.id"
                    value={isNew ? anios[0] && anios[0].id : trimestreVigenteEntity.anio1.id}
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
                <Button tag={Link} id="cancel-save" to="/entity/trimestre-vigente" replace color="info">
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
  anios: storeState.anio.entities,
  trimestreVigenteEntity: storeState.trimestreVigente.entity,
  loading: storeState.trimestreVigente.loading,
  updating: storeState.trimestreVigente.updating,
  updateSuccess: storeState.trimestreVigente.updateSuccess
});

const mapDispatchToProps = {
  getAnios,
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
)(TrimestreVigenteUpdate);
