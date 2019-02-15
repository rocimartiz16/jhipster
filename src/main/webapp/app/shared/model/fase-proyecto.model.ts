import { IActividadProyecto } from 'app/shared/model/actividad-proyecto.model';
import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IFaseProyecto {
  id?: number;
  nombreFase?: string;
  estadoFase?: string;
  actividadProyecto7S?: IActividadProyecto[];
  proyecto?: IProyecto;
}

export const defaultValue: Readonly<IFaseProyecto> = {};
