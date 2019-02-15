import { Moment } from 'moment';
import { IModalidad } from 'app/shared/model/modalidad.model';
import { IDia } from 'app/shared/model/dia.model';
import { IFichaTrimestre } from 'app/shared/model/ficha-trimestre.model';
import { IAmbiente } from 'app/shared/model/ambiente.model';
import { IInstructor } from 'app/shared/model/instructor.model';
import { IVersionHorario } from 'app/shared/model/version-horario.model';

export interface IHorario {
  id?: number;
  horaInicio?: Moment;
  horaFin?: Moment;
  modalidad?: IModalidad;
  dia?: IDia;
  fichaTrimestre?: IFichaTrimestre;
  ambiente?: IAmbiente;
  instructor?: IInstructor;
  versionHorario?: IVersionHorario;
}

export const defaultValue: Readonly<IHorario> = {};
