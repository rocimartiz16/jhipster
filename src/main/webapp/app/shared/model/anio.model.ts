import { IVinculacionInstructor } from 'app/shared/model/vinculacion-instructor.model';
import { ITrimestreVigente } from 'app/shared/model/trimestre-vigente.model';

export const enum Estado {
  Habilitado = 'Habilitado',
  Inhabilitado = 'Inhabilitado'
}

export interface IAnio {
  id?: number;
  numeroAnio?: number;
  estado?: Estado;
  vinculacionInstructors?: IVinculacionInstructor[];
  trimestreVigentes?: ITrimestreVigente[];
}

export const defaultValue: Readonly<IAnio> = {};
