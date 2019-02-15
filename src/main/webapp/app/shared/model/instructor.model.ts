import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
import { IAreaInstructor } from 'app/shared/model/area-instructor.model';
import { IDisponibilidadCompetencias } from 'app/shared/model/disponibilidad-competencias.model';
import { IDisponibilidadHoraria } from 'app/shared/model/disponibilidad-horaria.model';
import { IHorario } from 'app/shared/model/horario.model';
import { ICliente } from 'app/shared/model/cliente.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IInstructor {
  id?: number;
  estado?: Estado;
  vinculacionInstructors?: IVinculacionInstructor[];
  areaInstructors?: IAreaInstructor[];
  disponibilidadCompetencias?: IDisponibilidadCompetencias[];
  disponibilidadHorarias?: IDisponibilidadHoraria[];
  horarios?: IHorario[];
  cliente?: ICliente;
}

export const defaultValue: Readonly<IInstructor> = {};
