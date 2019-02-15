import { IInstructor } from 'app/shared/model/instructor.model';
import { IDocumento } from 'app/shared/model/documento.model';

export interface ICliente {
  id?: number;
  numeroDocumento?: string;
  primerNombre?: string;
  segundoNombre?: string;
  primerApellido?: string;
  segundoApellido?: string;
  instructors?: IInstructor[];
  documento?: IDocumento;
}

export const defaultValue: Readonly<ICliente> = {};
