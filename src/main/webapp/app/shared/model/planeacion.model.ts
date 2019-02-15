export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IPlaneacion {
  id?: number;
  nombrePlaneacion?: string;
  estado?: Estado;
}

export const defaultValue: Readonly<IPlaneacion> = {};
