export interface IAttributValue {
  id?: number;
  value?: string;
  attributId?: number;
}

export class AttributValue implements IAttributValue {
  constructor(public id?: number, public value?: string, public attributId?: number) {}
}
