import { IHorario } from 'app/shared/model/horario.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IModalidad {
  id?: number;
  nombre?: string;
  estado?: Estado;
  color?: string;
  horarios?: IHorario[];
}

export const defaultValue: Readonly<IModalidad> = {};
