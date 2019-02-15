import { Moment } from 'moment';
import { IVersionHorario } from 'app/shared/model/version-horario.model';
import { IAnio } from 'app/shared/model/anio.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface ITrimestreVigente {
  id?: number;
  anio?: number;
  trimestreProgramado?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  estado?: Estado;
  versionHorarios?: IVersionHorario[];
  anio1?: IAnio;
}

export const defaultValue: Readonly<ITrimestreVigente> = {};
