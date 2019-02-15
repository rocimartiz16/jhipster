import { IAreaInstructor } from 'app/shared/model/area-instructor.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IArea {
  id?: number;
  nombreArea?: string;
  estado?: Estado;
  urlLogo?: string;
  areaInstructors?: IAreaInstructor[];
}

export const defaultValue: Readonly<IArea> = {};
