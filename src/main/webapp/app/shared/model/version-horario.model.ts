import { IHorario } from 'app/shared/model/horario.model';
import { ITrimestreVigente } from 'app/shared/model/trimestre-vigente.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IVersionHorario {
  id?: number;
  numeroVersion?: number;
  estado?: Estado;
  trimestreVigenteId?: number;
  horarios?: IHorario[];
  versionTrimestre?: ITrimestreVigente;
}

export const defaultValue: Readonly<IVersionHorario> = {};
