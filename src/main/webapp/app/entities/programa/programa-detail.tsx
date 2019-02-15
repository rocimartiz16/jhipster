import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './programa.reducer';
import { IPrograma } from 'app/shared/model/programa.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProgramaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProgramaDetail extends React.Component<IProgramaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { programaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.programa.detail.title">Programa</Translate> [<b>{programaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="codigo">
                <Translate contentKey="ghostceetApp.programa.codigo">Codigo</Translate>
              </span>
            </dt>
            <dd>{programaEntity.codigo}</dd>
            <dt>
              <span id="version">
                <Translate contentKey="ghostceetApp.programa.version">Version</Translate>
              </span>
            </dt>
            <dd>{programaEntity.version}</dd>
            <dt>
              <span id="nombre">
                <Translate contentKey="ghostceetApp.programa.nombre">Nombre</Translate>
              </span>
            </dt>
            <dd>{programaEntity.nombre}</dd>
            <dt>
              <span id="sigla">
                <Translate contentKey="ghostceetApp.programa.sigla">Sigla</Translate>
              </span>
            </dt>
            <dd>{programaEntity.sigla}</dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.programa.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{programaEntity.estado}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.programa.nivelFormacion">Nivel Formacion</Translate>
            </dt>
            <dd>{programaEntity.nivelFormacion ? programaEntity.nivelFormacion.nivelFormacion : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/programa" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/programa/${programaEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ programa }: IRootState) => ({
  programaEntity: programa.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProgramaDetail);
