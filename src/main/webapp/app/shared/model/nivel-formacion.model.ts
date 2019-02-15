import { IPrograma } from 'app/shared/model/programa.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface INivelFormacion {
  id?: number;
  nivelFormacion?: string;
  estado?: Estado;
  programa1S?: IPrograma[];
}

export const defaultValue: Readonly<INivelFormacion> = {};
