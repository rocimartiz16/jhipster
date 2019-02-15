import { IJornadaInstructor } from 'app/shared/model/jornada-instructor.model';

export interface IDiaJornada {
  id?: number;
  horaInicio?: number;
  horaFin?: number;
  jornadaInstructor?: IJornadaInstructor;
}

export const defaultValue: Readonly<IDiaJornada> = {};
