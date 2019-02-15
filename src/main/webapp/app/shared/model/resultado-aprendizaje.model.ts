import { IResultadosVistos } from 'app/shared/model/resultados-vistos.model';
import { ILimitacionAmbiente } from 'app/shared/model/limitacion-ambiente.model';

export interface IResultadoAprendizaje {
  id?: number;
  codigo?: string;
  descripcion?: string;
  resultadosVistos?: IResultadosVistos[];
  limitacionAmbientes?: ILimitacionAmbiente[];
}

export const defaultValue: Readonly<IResultadoAprendizaje> = {};
