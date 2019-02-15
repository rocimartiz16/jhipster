import { IPlaneacionActividad } from 'app/shared/model/planeacion-actividad.model';
import { IFaseProyecto } from 'app/shared/model/fase-proyecto.model';

export interface IActividadProyecto {
  id?: number;
  numeroActividad?: number;
  nombreActividad?: string;
  planeacionActividads?: IPlaneacionActividad[];
  faseProyecto?: IFaseProyecto;
}

export const defaultValue: Readonly<IActividadProyecto> = {};
