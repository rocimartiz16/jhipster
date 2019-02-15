import { Moment } from 'moment';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';
import { IPrograma } from 'app/shared/model/programa.model';
import { IEstadoFicha } from 'app/shared/model/estado-ficha.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IFicha {
  id?: number;
  numero?: string;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  ruta?: string;
  estado?: Estado;
  fichaTrimestres?: IFichaTrimestre[];
  programa?: IPrograma;
  estadoFicha?: IEstadoFicha;
}

export const defaultValue: Readonly<IFicha> = {};
