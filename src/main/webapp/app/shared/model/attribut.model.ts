import { IAttributValue } from 'app/shared/model/attribut-value.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IAttribut {
  id?: number;
  name?: string;
  values?: IAttributValue[];
  category?: ICategory;
}

export class Attribut implements IAttribut {
  constructor(public id?: number, public name?: string, public values?: IAttributValue[], public category?: ICategory) {}
}
