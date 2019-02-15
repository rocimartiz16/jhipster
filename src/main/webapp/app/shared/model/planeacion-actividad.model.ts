import { IPlaneacionTrimestre } from 'app/shared/model/planeacion-trimestre.model';
import { IActividadProyecto } from 'app/shared/model/actividad-proyecto.model';

export interface IPlaneacionActividad {
  id?: number;
  planeacionTrimestre?: IPlaneacionTrimestre;
  actividadProyecto?: IActividadProyecto;
}

export const defaultValue: Readonly<IPlaneacionActividad> = {};
