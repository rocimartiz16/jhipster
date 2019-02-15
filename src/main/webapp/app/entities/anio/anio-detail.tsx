import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './anio.reducer';
import { IAnio } from 'app/shared/model/anio.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AnioDetail extends React.Component<IAnioDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { anioEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.anio.detail.title">Anio</Translate> [<b>{anioEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="numeroAnio">
                <Translate contentKey="ghostceetApp.anio.numeroAnio">Numero Anio</Translate>
              </span>
            </dt>
            <dd>{anioEntity.numeroAnio}</dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.anio.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{anioEntity.estado}</dd>
          </dl>
          <Button tag={Link} to="/entity/anio" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/anio/${anioEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ anio }: IRootState) => ({
  anioEntity: anio.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnioDetail);
