import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import planeacion, {
  PlaneacionState
} from 'app/entities/planeacion/planeacion.reducer';
// prettier-ignore
import nivelFormacion, {
  NivelFormacionState
} from 'app/entities/nivel-formacion/nivel-formacion.reducer';
// prettier-ignore
import programa, {
  ProgramaState
} from 'app/entities/programa/programa.reducer';
// prettier-ignore
import proyecto, {
  ProyectoState
} from 'app/entities/proyecto/proyecto.reducer';
// prettier-ignore
import competencia, {
  CompetenciaState
} from 'app/entities/competencia/competencia.reducer';
// prettier-ignore
import resultadoAprendizaje, {
  ResultadoAprendizajeState
} from 'app/entities/resultado-aprendizaje/resultado-aprendizaje.reducer';
// prettier-ignore
import jornada, {
  JornadaState
} from 'app/entities/jornada/jornada.reducer';
// prettier-ignore
import trimestre, {
  TrimestreState
} from 'app/entities/trimestre/trimestre.reducer';
// prettier-ignore
import planeacionTrimestre, {
  PlaneacionTrimestreState
} from 'app/entities/planeacion-trimestre/planeacion-trimestre.reducer';
// prettier-ignore
import faseProyecto, {
  FaseProyectoState
} from 'app/entities/fase-proyecto/fase-proyecto.reducer';
// prettier-ignore
import actividadProyecto, {
  ActividadProyectoState
} from 'app/entities/actividad-proyecto/actividad-proyecto.reducer';
// prettier-ignore
import estadoFicha, {
  EstadoFichaState
} from 'app/entities/estado-ficha/estado-ficha.reducer';
// prettier-ignore
import ficha, {
  FichaState
} from 'app/entities/ficha/ficha.reducer';
// prettier-ignore
import fichaTrimestre, {
  FichaTrimestreState
} from 'app/entities/ficha-trimestre/ficha-trimestre.reducer';
// prettier-ignore
import resultadosVistos, {
  ResultadosVistosState
} from 'app/entities/resultados-vistos/resultados-vistos.reducer';
// prettier-ignore
import planeacionActividad, {
  PlaneacionActividadState
} from 'app/entities/planeacion-actividad/planeacion-actividad.reducer';
// prettier-ignore
import documento, {
  DocumentoState
} from 'app/entities/documento/documento.reducer';
// prettier-ignore
import cliente, {
  ClienteState
} from 'app/entities/cliente/cliente.reducer';
// prettier-ignore
import vinculacion, {
  VinculacionState
} from 'app/entities/vinculacion/vinculacion.reducer';
// prettier-ignore
import anio, {
  AnioState
} from 'app/entities/anio/anio.reducer';
// prettier-ignore
import instructor, {
  InstructorState
} from 'app/entities/instructor/instructor.reducer';
// prettier-ignore
import vinculacionInstructor, {
  VinculacionInstructorState
} from 'app/entities/vinculacion-instructor/vinculacion-instructor.reducer';
// prettier-ignore
import jornadaInstructor, {
  JornadaInstructorState
} from 'app/entities/jornada-instructor/jornada-instructor.reducer';
// prettier-ignore
import diaJornada, {
  DiaJornadaState
} from 'app/entities/dia-jornada/dia-jornada.reducer';
// prettier-ignore
import area, {
  AreaState
} from 'app/entities/area/area.reducer';
// prettier-ignore
import areaInstructor, {
  AreaInstructorState
} from 'app/entities/area-instructor/area-instructor.reducer';
// prettier-ignore
import disponibilidadCompetencias, {
  DisponibilidadCompetenciasState
} from 'app/entities/disponibilidad-competencias/disponibilidad-competencias.reducer';
// prettier-ignore
import disponibilidadHoraria, {
  DisponibilidadHorariaState
} from 'app/entities/disponibilidad-horaria/disponibilidad-horaria.reducer';
// prettier-ignore
import tipoAmbiente, {
  TipoAmbienteState
} from 'app/entities/tipo-ambiente/tipo-ambiente.reducer';
// prettier-ignore
import sede, {
  SedeState
} from 'app/entities/sede/sede.reducer';
// prettier-ignore
import ambiente, {
  AmbienteState
} from 'app/entities/ambiente/ambiente.reducer';
// prettier-ignore
import limitacionAmbiente, {
  LimitacionAmbienteState
} from 'app/entities/limitacion-ambiente/limitacion-ambiente.reducer';
// prettier-ignore
import modalidad, {
  ModalidadState
} from 'app/entities/modalidad/modalidad.reducer';
// prettier-ignore
import dia, {
  DiaState
} from 'app/entities/dia/dia.reducer';
// prettier-ignore
import trimestreVigente, {
  TrimestreVigenteState
} from 'app/entities/trimestre-vigente/trimestre-vigente.reducer';
// prettier-ignore
import versionHorario, {
  VersionHorarioState
} from 'app/entities/version-horario/version-horario.reducer';
// prettier-ignore
import horario, {
  HorarioState
} from 'app/entities/horario/horario.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly planeacion: PlaneacionState;
  readonly nivelFormacion: NivelFormacionState;
  readonly programa: ProgramaState;
  readonly proyecto: ProyectoState;
  readonly competencia: CompetenciaState;
  readonly resultadoAprendizaje: ResultadoAprendizajeState;
  readonly jornada: JornadaState;
  readonly trimestre: TrimestreState;
  readonly planeacionTrimestre: PlaneacionTrimestreState;
  readonly faseProyecto: FaseProyectoState;
  readonly actividadProyecto: ActividadProyectoState;
  readonly estadoFicha: EstadoFichaState;
  readonly ficha: FichaState;
  readonly fichaTrimestre: FichaTrimestreState;
  readonly resultadosVistos: ResultadosVistosState;
  readonly planeacionActividad: PlaneacionActividadState;
  readonly documento: DocumentoState;
  readonly cliente: ClienteState;
  readonly vinculacion: VinculacionState;
  readonly anio: AnioState;
  readonly instructor: InstructorState;
  readonly vinculacionInstructor: VinculacionInstructorState;
  readonly jornadaInstructor: JornadaInstructorState;
  readonly diaJornada: DiaJornadaState;
  readonly area: AreaState;
  readonly areaInstructor: AreaInstructorState;
  readonly disponibilidadCompetencias: DisponibilidadCompetenciasState;
  readonly disponibilidadHoraria: DisponibilidadHorariaState;
  readonly tipoAmbiente: TipoAmbienteState;
  readonly sede: SedeState;
  readonly ambiente: AmbienteState;
  readonly limitacionAmbiente: LimitacionAmbienteState;
  readonly modalidad: ModalidadState;
  readonly dia: DiaState;
  readonly trimestreVigente: TrimestreVigenteState;
  readonly versionHorario: VersionHorarioState;
  readonly horario: HorarioState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  planeacion,
  nivelFormacion,
  programa,
  proyecto,
  competencia,
  resultadoAprendizaje,
  jornada,
  trimestre,
  planeacionTrimestre,
  faseProyecto,
  actividadProyecto,
  estadoFicha,
  ficha,
  fichaTrimestre,
  resultadosVistos,
  planeacionActividad,
  documento,
  cliente,
  vinculacion,
  anio,
  instructor,
  vinculacionInstructor,
  jornadaInstructor,
  diaJornada,
  area,
  areaInstructor,
  disponibilidadCompetencias,
  disponibilidadHoraria,
  tipoAmbiente,
  sede,
  ambiente,
  limitacionAmbiente,
  modalidad,
  dia,
  trimestreVigente,
  versionHorario,
  horario,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
