import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/planeacion">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.planeacion" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/nivel-formacion">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.nivelFormacion" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/programa">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.programa" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/proyecto">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.proyecto" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/competencia">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.competencia" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/resultado-aprendizaje">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.resultadoAprendizaje" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/jornada">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.jornada" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/trimestre">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.trimestre" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/planeacion-trimestre">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.planeacionTrimestre" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/fase-proyecto">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.faseProyecto" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/actividad-proyecto">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.actividadProyecto" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/estado-ficha">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.estadoFicha" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ficha">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.ficha" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ficha-trimestre">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.fichaTrimestre" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/resultados-vistos">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.resultadosVistos" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/planeacion-actividad">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.planeacionActividad" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/documento">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.documento" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cliente">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.cliente" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vinculacion">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.vinculacion" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/anio">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.anio" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/instructor">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.instructor" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/vinculacion-instructor">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.vinculacionInstructor" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/jornada-instructor">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.jornadaInstructor" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/dia-jornada">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.diaJornada" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/area">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.area" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/area-instructor">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.areaInstructor" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/disponibilidad-competencias">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.disponibilidadCompetencias" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/disponibilidad-horaria">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.disponibilidadHoraria" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tipo-ambiente">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.tipoAmbiente" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/sede">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.sede" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ambiente">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.ambiente" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/limitacion-ambiente">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.limitacionAmbiente" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/modalidad">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.modalidad" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/dia">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.dia" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/trimestre-vigente">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.trimestreVigente" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/version-horario">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.versionHorario" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/horario">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.horario" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
