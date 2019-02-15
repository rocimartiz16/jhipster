import { IAmbiente } from 'app/shared/model/ambiente.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface ISede {
  id?: number;
  nombreSede?: string;
  direccion?: string;
  estado?: Estado;
  ambientes?: IAmbiente[];
}

export const defaultValue: Readonly<ISede> = {};
