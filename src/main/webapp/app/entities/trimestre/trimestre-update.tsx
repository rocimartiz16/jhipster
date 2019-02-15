import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IJornada } from 'app/shared/model/jornada.model';
import { getEntities as getJornadas } from 'app/entities/jornada/jornada.reducer';
import { getEntity, updateEntity, createEntity, reset } from './trimestre.reducer';
import { ITrimestre } from 'app/shared/model/trimestre.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITrimestreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITrimestreUpdateState {
  isNew: boolean;
  jornadaId: string;
}

export class TrimestreUpdate extends React.Component<ITrimestreUpdateProps, ITrimestreUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      jornadaId: '0',
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

    this.props.getJornadas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { trimestreEntity } = this.props;
      const entity = {
        ...trimestreEntity,
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
    this.props.history.push('/entity/trimestre');
  };

  render() {
    const { trimestreEntity, jornadas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.trimestre.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.trimestre.home.createOrEditLabel">Create or edit a Trimestre</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : trimestreEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="trimestre-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nombreTrimestreLabel" for="nombreTrimestre">
                    <Translate contentKey="ghostceetApp.trimestre.nombreTrimestre">Nombre Trimestre</Translate>
                  </Label>
                  <AvField
                    id="trimestre-nombreTrimestre"
                    type="text"
                    name="nombreTrimestre"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 20, errorMessage: translate('entity.validation.maxlength', { max: 20 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">
                    <Translate contentKey="ghostceetApp.trimestre.estado">Estado</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && trimestreEntity.estado) || 'Habilitado'}
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
                  <Label for="jornada.nombreJornada">
                    <Translate contentKey="ghostceetApp.trimestre.jornada">Jornada</Translate>
                  </Label>
                  <AvInput
                    id="trimestre-jornada"
                    type="select"
                    className="form-control"
                    name="jornada.id"
                    value={isNew ? jornadas[0] && jornadas[0].id : trimestreEntity.jornada.id}
                  >
                    {jornadas
                      ? jornadas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombreJornada}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/trimestre" replace color="info">
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
  jornadas: storeState.jornada.entities,
  trimestreEntity: storeState.trimestre.entity,
  loading: storeState.trimestre.loading,
  updating: storeState.trimestre.updating,
  updateSuccess: storeState.trimestre.updateSuccess
});

const mapDispatchToProps = {
  getJornadas,
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
)(TrimestreUpdate);
