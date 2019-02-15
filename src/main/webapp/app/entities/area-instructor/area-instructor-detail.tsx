import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './area-instructor.reducer';
import { IAreaInstructor } from 'app/shared/model/area-instructor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAreaInstructorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AreaInstructorDetail extends React.Component<IAreaInstructorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { areaInstructorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="ghostceetApp.areaInstructor.detail.title">AreaInstructor</Translate> [<b>{areaInstructorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="estado">
                <Translate contentKey="ghostceetApp.areaInstructor.estado">Estado</Translate>
              </span>
            </dt>
            <dd>{areaInstructorEntity.estado}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.areaInstructor.area">Area</Translate>
            </dt>
            <dd>{areaInstructorEntity.area ? areaInstructorEntity.area.nombreArea : ''}</dd>
            <dt>
              <Translate contentKey="ghostceetApp.areaInstructor.instructor">Instructor</Translate>
            </dt>
            <dd>{areaInstructorEntity.instructor ? areaInstructorEntity.instructor.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/area-instructor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/area-instructor/${areaInstructorEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ areaInstructor }: IRootState) => ({
  areaInstructorEntity: areaInstructor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AreaInstructorDetail);
