import { IResultadosVistos } from 'app/shared/model/resultados-vistos.model';
import { IHorario } from 'app/shared/model/horario.model';
import { IFicha } from 'app/shared/model/ficha.model';

export interface IFichaTrimestre {
  id?: number;
  resultadosVistos?: IResultadosVistos[];
  horarios?: IHorario[];
  ficha?: IFicha;
}

export const defaultValue: Readonly<IFichaTrimestre> = {};
