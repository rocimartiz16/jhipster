import { IResultadoAprendizaje } from 'app/shared/model/resultado-aprendizaje.model';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';

export interface IResultadosVistos {
  id?: number;
  resultadoAprendizaje?: IResultadoAprendizaje;
  fichaTrimestre?: IFichaTrimestre;
}

export const defaultValue: Readonly<IResultadosVistos> = {};
