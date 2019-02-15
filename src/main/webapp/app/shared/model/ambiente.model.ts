import { ILimitacionAmbiente } from 'app/shared/model/limitacion-ambiente.model';
import { IHorario } from 'app/shared/model/horario.model';
import { ITipoAmbiente } from 'app/shared/model/tipo-ambiente.model';
import { ISede } from 'app/shared/model/sede.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IAmbiente {
  id?: number;
  numeroAmbiente?: string;
  descripcion?: string;
  estado?: Estado;
  limitacion?: string;
  limitacionAmbientes?: ILimitacionAmbiente[];
  horarios?: IHorario[];
  tipoAmbiente?: ITipoAmbiente;
  sede?: ISede;
}

export const defaultValue: Readonly<IAmbiente> = {};
