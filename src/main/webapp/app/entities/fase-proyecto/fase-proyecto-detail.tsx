import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fase-proyecto.reducer';
import { IFaseProyecto } from 'app/shared/model/fase-proyecto.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFaseProyectoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FaseProyectoDetail extends React.Component<IFaseProyectoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { faseProyectoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.faseProyecto.detail.title">FaseProyecto</Translate> [<b>{faseProyectoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombreFase">
                <Translate contentKey="ghostceetApp.faseProyecto.nombreFase">Nombre Fase</Translate>
              </span>
            </dt>
            <dd>{faseProyectoEntity.nombreFase}</dd>
            <dt>
              <span id="estadoFase">
                <Translate contentKey="ghostceetApp.faseProyecto.estadoFase">Estado Fase</Translate>
              </span>
            </dt>
            <dd>{faseProyectoEntity.estadoFase}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.faseProyecto.proyecto">Proyecto</Translate>
            </dt>
            <dd>{faseProyectoEntity.proyecto ? faseProyectoEntity.proyecto.codigo : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/fase-proyecto" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/fase-proyecto/${faseProyectoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ faseProyecto }: IRootState) => ({
  faseProyectoEntity: faseProyecto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FaseProyectoDetail);
