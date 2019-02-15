import { IPlaneacionTrimestre } from 'app/shared/model/planeacion-trimestre.model';
import { IJornada } from 'app/shared/model/jornada.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface ITrimestre {
  id?: number;
  nombreTrimestre?: string;
  estado?: Estado;
  planeacionTrimestre5S?: IPlaneacionTrimestre[];
  jornada?: IJornada;
}

export const defaultValue: Readonly<ITrimestre> = {};
