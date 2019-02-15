import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';
import { IInstructor } from 'app/shared/model/instructor.model';

export interface IDisponibilidadHoraria {
  id?: number;
  vinculacionInstructor?: IVinculacionInstructor;
  jornada?: IJornadaInstructor;
  instructor?: IInstructor;
}

export const defaultValue: Readonly<IDisponibilidadHoraria> = {};
