import { IResultadoAprendizaje } from 'app/shared/model/resultado-aprendizaje.model';
import { IAmbiente } from 'app/shared/model/ambiente.model';

export interface ILimitacionAmbiente {
  id?: number;
  resultadoAprendizaje?: IResultadoAprendizaje;
  ambiente?: IAmbiente;
}

export const defaultValue: Readonly<ILimitacionAmbiente> = {};
