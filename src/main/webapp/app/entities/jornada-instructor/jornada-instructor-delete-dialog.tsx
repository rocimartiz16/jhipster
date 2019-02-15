import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './jornada-instructor.reducer';

export interface IJornadaInstructorDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class JornadaInstructorDeleteDialog extends React.Component<IJornadaInstructorDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.jornadaInstructorEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { jornadaInstructorEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="ghostceetApp.jornadaInstructor.delete.question">
          <Translate contentKey="ghostceetApp.jornadaInstructor.delete.question" interpolate={{ id: jornadaInstructorEntity.id }}>
            Are you sure you want to delete this JornadaInstructor?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-jornadaInstructor" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ jornadaInstructor }: IRootState) => ({
  jornadaInstructorEntity: jornadaInstructor.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(JornadaInstructorDeleteDialog);
