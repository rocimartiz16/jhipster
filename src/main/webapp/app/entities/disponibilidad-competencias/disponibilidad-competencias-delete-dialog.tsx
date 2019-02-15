import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDisponibilidadCompetencias } from 'app/shared/model/disponibilidad-competencias.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './disponibilidad-competencias.reducer';

export interface IDisponibilidadCompetenciasDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DisponibilidadCompetenciasDeleteDialog extends React.Component<IDisponibilidadCompetenciasDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.disponibilidadCompetenciasEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { disponibilidadCompetenciasEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="ghostceetApp.disponibilidadCompetencias.delete.question">
          <Translate
            contentKey="ghostceetApp.disponibilidadCompetencias.delete.question"
            interpolate={{ id: disponibilidadCompetenciasEntity.id }}
          >
            Are you sure you want to delete this DisponibilidadCompetencias?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-disponibilidadCompetencias" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ disponibilidadCompetencias }: IRootState) => ({
  disponibilidadCompetenciasEntity: disponibilidadCompetencias.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DisponibilidadCompetenciasDeleteDialog);
