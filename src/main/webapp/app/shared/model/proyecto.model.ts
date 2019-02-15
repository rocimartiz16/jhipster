import { IFaseProyecto } from 'app/shared/model/fase-proyecto.model';
import { IPrograma } from 'app/shared/model/programa.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IProyecto {
  id?: number;
  codigo?: string;
  estado?: Estado;
  nombreProyecto?: string;
  faseProyecto6S?: IFaseProyecto[];
  programa?: IPrograma;
}

export const defaultValue: Readonly<IProyecto> = {};
