import { Moment } from 'moment';
import { IDisponibilidadCompetencias } from 'app/shared/model/disponibilidad-competencias.model';
import { IDisponibilidadHoraria } from 'app/shared/model/disponibilidad-horaria.model';
import { IInstructor } from 'app/shared/model/instructor.model';
import { IAnio } from 'app/shared/model/anio.model';
import { IVinculacion } from 'app/shared/model/vinculacion.model';

export interface IVinculacionInstructor {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  disponibilidadCompetencias?: IDisponibilidadCompetencias[];
  disponibilidadHorarias?: IDisponibilidadHoraria[];
  instructor?: IInstructor;
  anio1?: IAnio;
  vinculacion?: IVinculacion;
}

export const defaultValue: Readonly<IVinculacionInstructor> = {};
