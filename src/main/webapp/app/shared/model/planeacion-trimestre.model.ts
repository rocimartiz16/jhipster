import { IPlaneacionActividad } from 'app/shared/model/planeacion-actividad.model';
import { ITrimestre } from 'app/shared/model/trimestre.model';

export interface IPlaneacionTrimestre {
  id?: number;
  planeacionActividads?: IPlaneacionActividad[];
  trimestre?: ITrimestre;
}

export const defaultValue: Readonly<IPlaneacionTrimestre> = {};
