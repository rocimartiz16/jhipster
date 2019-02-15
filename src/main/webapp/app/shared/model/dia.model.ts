import { IHorario } from 'app/shared/model/horario.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IDia {
  id?: number;
  nombreDia?: string;
  estado?: Estado;
  color?: string;
  horarios?: IHorario[];
}

export const defaultValue: Readonly<IDia> = {};
