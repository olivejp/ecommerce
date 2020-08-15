import { IAttribut } from 'app/shared/model/attribut.model';

export interface IAttributValue {
  id?: number;
  value?: string;
  attribut?: IAttribut;
}

export class AttributValue implements IAttributValue {
  constructor(public id?: number, public value?: string, public attribut?: IAttribut) {}
}
