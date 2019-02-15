import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResultadoAprendizaje } from 'app/shared/model/resultado-aprendizaje.model';
import { getEntities as getResultadoAprendizajes } from 'app/entities/resultado-aprendizaje/resultado-aprendizaje.reducer';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';
import { getEntities as getFichaTrimestres } from 'app/entities/ficha-trimestre/ficha-trimestre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resultados-vistos.reducer';
import { IResultadosVistos } from 'app/shared/model/resultados-vistos.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResultadosVistosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IResultadosVistosUpdateState {
  isNew: boolean;
  resultadoAprendizajeId: string;
  fichaTrimestreId: string;
}

export class ResultadosVistosUpdate extends React.Component<IResultadosVistosUpdateProps, IResultadosVistosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      resultadoAprendizajeId: '0',
      fichaTrimestreId: '0',
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

    this.props.getResultadoAprendizajes();
    this.props.getFichaTrimestres();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { resultadosVistosEntity } = this.props;
      const entity = {
        ...resultadosVistosEntity,
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
    this.props.history.push('/entity/resultados-vistos');
  };

  render() {
    const { resultadosVistosEntity, resultadoAprendizajes, fichaTrimestres, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ghostceetApp.resultadosVistos.home.createOrEditLabel">
              <Translate contentKey="ghostceetApp.resultadosVistos.home.createOrEditLabel">Create or edit a ResultadosVistos</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : resultadosVistosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="resultados-vistos-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="resultadoAprendizaje.id">
                    <Translate contentKey="ghostceetApp.resultadosVistos.resultadoAprendizaje">Resultado Aprendizaje</Translate>
                  </Label>
                  <AvInput
                    id="resultados-vistos-resultadoAprendizaje"
                    type="select"
                    className="form-control"
                    name="resultadoAprendizaje.id"
                    value={isNew ? resultadoAprendizajes[0] && resultadoAprendizajes[0].id : resultadosVistosEntity.resultadoAprendizaje.id}
                  >
                    {resultadoAprendizajes
                      ? resultadoAprendizajes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="fichaTrimestre.id">
                    <Translate contentKey="ghostceetApp.resultadosVistos.fichaTrimestre">Ficha Trimestre</Translate>
                  </Label>
                  <AvInput
                    id="resultados-vistos-fichaTrimestre"
                    type="select"
                    className="form-control"
                    name="fichaTrimestre.id"
                    value={isNew ? fichaTrimestres[0] && fichaTrimestres[0].id : resultadosVistosEntity.fichaTrimestre.id}
                  >
                    {fichaTrimestres
                      ? fichaTrimestres.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/resultados-vistos" replace color="info">
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
  resultadoAprendizajes: storeState.resultadoAprendizaje.entities,
  fichaTrimestres: storeState.fichaTrimestre.entities,
  resultadosVistosEntity: storeState.resultadosVistos.entity,
  loading: storeState.resultadosVistos.loading,
  updating: storeState.resultadosVistos.updating,
  updateSuccess: storeState.resultadosVistos.updateSuccess
});

const mapDispatchToProps = {
  getResultadoAprendizajes,
  getFichaTrimestres,
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
)(ResultadosVistosUpdate);
