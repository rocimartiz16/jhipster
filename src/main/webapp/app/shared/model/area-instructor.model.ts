import { IArea } from 'app/shared/model/area.model';
import { IInstructor } from 'app/shared/model/instructor.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IAreaInstructor {
  id?: number;
  estado?: Estado;
  area?: IArea;
  instructor?: IInstructor;
}

export const defaultValue: Readonly<IAreaInstructor> = {};
