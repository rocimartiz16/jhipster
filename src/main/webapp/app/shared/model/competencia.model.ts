import { IPrograma } from 'app/shared/model/programa.model';

export interface ICompetencia {
  id?: number;
  codigo?: string;
  descripcion?: string;
  programa?: IPrograma;
}

export const defaultValue: Readonly<ICompetencia> = {};
