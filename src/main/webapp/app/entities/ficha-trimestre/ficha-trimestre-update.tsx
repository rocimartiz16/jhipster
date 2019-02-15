import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFicha } from 'app/shared/model/ficha.model';
import { getEntities as getFichas } from 'app/entities/ficha/ficha.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ficha-trimestre.reducer';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFichaTrimestreUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFichaTrimestreUpdateState {
  isNew: boolean;
  fichaId: string;
}

export class FichaTrimestreUpdate extends React.Component<IFichaTrimestreUpdateProps, IFichaTrimestreUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fichaId: '0',
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

    this.props.getFichas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { fichaTrimestreEntity } = this.props;
      const entity = {
        ...fichaTrimestreEntity,
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
    this.props.history.push('/entity/ficha-trimestre');
  };

  render() {
    const { fichaTrimestreEntity, fichas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.fichaTrimestre.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.fichaTrimestre.home.createOrEditLabel">Create or edit a FichaTrimestre</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fichaTrimestreEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ficha-trimestre-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="ficha.numero">
                    <Translate contentKey="ghostceetApp.fichaTrimestre.ficha">Ficha</Translate>
                  </Label>
                  <AvInput
                    id="ficha-trimestre-ficha"
                    type="select"
                    className="form-control"
                    name="ficha.id"
                    value={isNew ? fichas[0] && fichas[0].id : fichaTrimestreEntity.ficha.id}
                  >
                    {fichas
                      ? fichas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.numero}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ficha-trimestre" replace color="info">
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
  fichas: storeState.ficha.entities,
  fichaTrimestreEntity: storeState.fichaTrimestre.entity,
  loading: storeState.fichaTrimestre.loading,
  updating: storeState.fichaTrimestre.updating,
  updateSuccess: storeState.fichaTrimestre.updateSuccess
});

const mapDispatchToProps = {
  getFichas,
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
)(FichaTrimestreUpdate);
