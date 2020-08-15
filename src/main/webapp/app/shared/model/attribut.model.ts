import { IAttributValue } from 'app/shared/model/attribut-value.model';

export interface IAttribut {
  id?: number;
  name?: string;
  values?: IAttributValue[];
  categoryId?: number;
}

export class Attribut implements IAttribut {
  constructor(public id?: number, public name?: string, public values?: IAttributValue[], public categoryId?: number) {}
}
