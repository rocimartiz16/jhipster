import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Planeacion from './planeacion';
import NivelFormacion from './nivel-formacion';
import Programa from './programa';
import Proyecto from './proyecto';
import Competencia from './competencia';
import ResultadoAprendizaje from './resultado-aprendizaje';
import Jornada from './jornada';
import Trimestre from './trimestre';
import PlaneacionTrimestre from './planeacion-trimestre';
import FaseProyecto from './fase-proyecto';
import ActividadProyecto from './actividad-proyecto';
import EstadoFicha from './estado-ficha';
import Ficha from './ficha';
import FichaTrimestre from './ficha-trimestre';
import ResultadosVistos from './resultados-vistos';
import PlaneacionActividad from './planeacion-actividad';
import Documento from './documento';
import Cliente from './cliente';
import Vinculacion from './vinculacion';
import Anio from './anio';
import Instructor from './instructor';
import VinculacionInstructor from './vinculacion-instructor';
import JornadaInstructor from './jornada-instructor';
import DiaJornada from './dia-jornada';
import Area from './area';
import AreaInstructor from './area-instructor';
import DisponibilidadCompetencias from './disponibilidad-competencias';
import DisponibilidadHoraria from './disponibilidad-horaria';
import TipoAmbiente from './tipo-ambiente';
import Sede from './sede';
import Ambiente from './ambiente';
import LimitacionAmbiente from './limitacion-ambiente';
import Modalidad from './modalidad';
import Dia from './dia';
import TrimestreVigente from './trimestre-vigente';
import VersionHorario from './version-horario';
import Horario from './horario';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/planeacion`} component={Planeacion} />
      <ErrorBoundaryRoute path={`${match.url}/nivel-formacion`} component={NivelFormacion} />
      <ErrorBoundaryRoute path={`${match.url}/programa`} component={Programa} />
      <ErrorBoundaryRoute path={`${match.url}/proyecto`} component={Proyecto} />
      <ErrorBoundaryRoute path={`${match.url}/competencia`} component={Competencia} />
      <ErrorBoundaryRoute path={`${match.url}/resultado-aprendizaje`} component={ResultadoAprendizaje} />
      <ErrorBoundaryRoute path={`${match.url}/jornada`} component={Jornada} />
      <ErrorBoundaryRoute path={`${match.url}/trimestre`} component={Trimestre} />
      <ErrorBoundaryRoute path={`${match.url}/planeacion-trimestre`} component={PlaneacionTrimestre} />
      <ErrorBoundaryRoute path={`${match.url}/fase-proyecto`} component={FaseProyecto} />
      <ErrorBoundaryRoute path={`${match.url}/actividad-proyecto`} component={ActividadProyecto} />
      <ErrorBoundaryRoute path={`${match.url}/estado-ficha`} component={EstadoFicha} />
      <ErrorBoundaryRoute path={`${match.url}/ficha`} component={Ficha} />
      <ErrorBoundaryRoute path={`${match.url}/ficha-trimestre`} component={FichaTrimestre} />
      <ErrorBoundaryRoute path={`${match.url}/resultados-vistos`} component={ResultadosVistos} />
      <ErrorBoundaryRoute path={`${match.url}/planeacion-actividad`} component={PlaneacionActividad} />
      <ErrorBoundaryRoute path={`${match.url}/documento`} component={Documento} />
      <ErrorBoundaryRoute path={`${match.url}/cliente`} component={Cliente} />
      <ErrorBoundaryRoute path={`${match.url}/vinculacion`} component={Vinculacion} />
      <ErrorBoundaryRoute path={`${match.url}/anio`} component={Anio} />
      <ErrorBoundaryRoute path={`${match.url}/instructor`} component={Instructor} />
      <ErrorBoundaryRoute path={`${match.url}/vinculacion-instructor`} component={VinculacionInstructor} />
      <ErrorBoundaryRoute path={`${match.url}/jornada-instructor`} component={JornadaInstructor} />
      <ErrorBoundaryRoute path={`${match.url}/dia-jornada`} component={DiaJornada} />
      <ErrorBoundaryRoute path={`${match.url}/area`} component={Area} />
      <ErrorBoundaryRoute path={`${match.url}/area-instructor`} component={AreaInstructor} />
      <ErrorBoundaryRoute path={`${match.url}/disponibilidad-competencias`} component={DisponibilidadCompetencias} />
      <ErrorBoundaryRoute path={`${match.url}/disponibilidad-horaria`} component={DisponibilidadHoraria} />
      <ErrorBoundaryRoute path={`${match.url}/tipo-ambiente`} component={TipoAmbiente} />
      <ErrorBoundaryRoute path={`${match.url}/sede`} component={Sede} />
      <ErrorBoundaryRoute path={`${match.url}/ambiente`} component={Ambiente} />
      <ErrorBoundaryRoute path={`${match.url}/limitacion-ambiente`} component={LimitacionAmbiente} />
      <ErrorBoundaryRoute path={`${match.url}/modalidad`} component={Modalidad} />
      <ErrorBoundaryRoute path={`${match.url}/dia`} component={Dia} />
      <ErrorBoundaryRoute path={`${match.url}/trimestre-vigente`} component={TrimestreVigente} />
      <ErrorBoundaryRoute path={`${match.url}/version-horario`} component={VersionHorario} />
      <ErrorBoundaryRoute path={`${match.url}/horario`} component={Horario} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
