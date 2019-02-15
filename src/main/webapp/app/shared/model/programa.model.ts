import { IProyecto } from 'app/shared/model/proyecto.model';
import { ICompetencia } from 'app/shared/model/competencia.model';
import { IFicha } from 'app/shared/model/ficha.model';
import { INivelFormacion } from 'app/shared/model/nivel-formacion.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IPrograma {
  id?: number;
  codigo?: string;
  version?: string;
  nombre?: string;
  sigla?: string;
  estado?: Estado;
  proyecto2S?: IProyecto[];
  competencia3S?: ICompetencia[];
  fichas?: IFicha[];
  nivelFormacion?: INivelFormacion;
}

export const defaultValue: Readonly<IPrograma> = {};
