import { IInstructor } from 'app/shared/model/instructor.model';
import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';

export interface IDisponibilidadCompetencias {
  id?: number;
  instructor?: IInstructor;
  vinculacionInstructor?: IVinculacionInstructor;
}

export const defaultValue: Readonly<IDisponibilidadCompetencias> = {};
