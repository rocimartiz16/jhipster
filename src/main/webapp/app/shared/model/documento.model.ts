import { ICliente } from 'app/shared/model/cliente.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IDocumento {
  id?: number;
  sigla?: string;
  nombre?: string;
  estado?: Estado;
  clientes?: ICliente[];
}

export const defaultValue: Readonly<IDocumento> = {};
