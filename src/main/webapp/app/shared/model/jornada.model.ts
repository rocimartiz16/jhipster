import { ITrimestre } from 'app/shared/model/trimestre.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IJornada {
  id?: number;
  siglaJornada?: string;
  nombreJornada?: string;
  descripcion?: string;
  imagenUrl?: string;
  estado?: Estado;
  trimestre4S?: ITrimestre[];
}

export const defaultValue: Readonly<IJornada> = {};
