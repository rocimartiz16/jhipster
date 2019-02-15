import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IVinculacion {
  id?: number;
  tipoVinculacion?: string;
  horas?: number;
  estado?: Estado;
  vinculacionInstructors?: IVinculacionInstructor[];
}

export const defaultValue: Readonly<IVinculacion> = {};
