import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './vinculacion.reducer';
import { IVinculacion } from 'app/shared/model/vinculacion.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVinculacionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVinculacionUpdateState {
  isNew: boolean;
}

export class VinculacionUpdate extends React.Component<IVinculacionUpdateProps, IVinculacionUpdateState> {
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
      const { vinculacionEntity } = this.props;
      const entity = {
        ...vinculacionEntity,
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
    this.props.history.push('/entity/vinculacion');
  };

  render() {
    const { vinculacionEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.vinculacion.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.vinculacion.home.createOrEditLabel">Create or edit a Vinculacion</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vinculacionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vinculacion-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tipoVinculacionLabel" for="tipoVinculacion">
                    <Translate contentKey="ghostceetApp.vinculacion.tipoVinculacion">Tipo Vinculacion</Translate>
                  </Label>
                  <AvField
                    id="vinculacion-tipoVinculacion"
                    type="text"
                    name="tipoVinculacion"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 40, errorMessage: translate('entity.validation.maxlength', { max: 40 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horasLabel" for="horas">
                    <Translate contentKey="ghostceetApp.vinculacion.horas">Horas</Translate>
                  </Label>
                  <AvField
                    id="vinculacion-horas"
                    type="string"
                    className="form-control"
                    name="horas"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.vinculacion.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="vinculacion-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && vinculacionEntity.estado) || 'Habilitado'}
                  >
                    <option value="Habilitado">
                      <Translate contentKey="ghostceetApp.Estado.Habilitado" />
                    </option>
                    <option value="Inhabilitado">
                      <Translate contentKey="ghostceetApp.Estado.Inhabilitado" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vinculacion" replace color="info">
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
  vinculacionEntity: storeState.vinculacion.entity,
  loading: storeState.vinculacion.loading,
  updating: storeState.vinculacion.updating,
  updateSuccess: storeState.vinculacion.updateSuccess
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
)(VinculacionUpdate);
