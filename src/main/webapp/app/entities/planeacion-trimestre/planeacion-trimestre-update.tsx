import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITrimestre } from 'app/shared/model/trimestre.model';
import { getEntities as getTrimestres } from 'app/entities/trimestre/trimestre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './planeacion-trimestre.reducer';
import { IPlaneacionTrimestre } from 'app/shared/model/planeacion-trimestre.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlaneacionTrimestreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPlaneacionTrimestreUpdateState {
  isNew: boolean;
  trimestreId: string;
}

export class PlaneacionTrimestreUpdate extends React.Component<IPlaneacionTrimestreUpdateProps, IPlaneacionTrimestreUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      trimestreId: '0',
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

    this.props.getTrimestres();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { planeacionTrimestreEntity } = this.props;
      const entity = {
        ...planeacionTrimestreEntity,
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
    this.props.history.push('/entity/planeacion-trimestre');
  };

  render() {
    const { planeacionTrimestreEntity, trimestres, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.planeacionTrimestre.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.planeacionTrimestre.home.createOrEditLabel">
                Create or edit a PlaneacionTrimestre
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : planeacionTrimestreEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="planeacion-trimestre-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="trimestre.id">
                    <Translate contentKey="ghostceetApp.planeacionTrimestre.trimestre">Trimestre</Translate>
                  </Label>
                  <AvInput
                    id="planeacion-trimestre-trimestre"
                    type="select"
                    className="form-control"
                    name="trimestre.id"
                    value={isNew ? trimestres[0] && trimestres[0].id : planeacionTrimestreEntity.trimestre.id}
                  >
                    {trimestres
                      ? trimestres.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/planeacion-trimestre" replace color="info">
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
  trimestres: storeState.trimestre.entities,
  planeacionTrimestreEntity: storeState.planeacionTrimestre.entity,
  loading: storeState.planeacionTrimestre.loading,
  updating: storeState.planeacionTrimestre.updating,
  updateSuccess: storeState.planeacionTrimestre.updateSuccess
});

const mapDispatchToProps = {
  getTrimestres,
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
)(PlaneacionTrimestreUpdate);
