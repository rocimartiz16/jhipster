import { IDiaJornada } from 'app/shared/model/dia-jornada.model';
import { IDisponibilidadHoraria } from 'app/shared/model/disponibilidad-horaria.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IJornadaInstructor {
  id?: number;
  nombreJornada?: string;
  descripcion?: string;
  estado?: Estado;
  diaJornadas?: IDiaJornada[];
  disponibilidadHorarias?: IDisponibilidadHoraria[];
}

export const defaultValue: Readonly<IJornadaInstructor> = {};
