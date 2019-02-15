import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './documento.reducer';
import { IDocumento } from 'app/shared/model/documento.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDocumentoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DocumentoDetail extends React.Component<IDocumentoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { documentoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.documento.detail.title">Documento</Translate> [<b>{documentoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="sigla">
                <Translate contentKey="ghostceetApp.documento.sigla">Sigla</Translate>
              </span>
            </dt>
            <dd>{documentoEntity.sigla}</dd>
            <dt>
              <span id="nombre">
                <Translate contentKey="ghostceetApp.documento.nombre">Nombre</Translate>
              </span>
            </dt>
            <dd>{documentoEntity.nombre}</dd>
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.documento.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{documentoEntity.estado}</dd>
          </dl>
          <Button tag={Link} to="/entity/documento" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/documento/${documentoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ documento }: IRootState) => ({
  documentoEntity: documento.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DocumentoDetail);
