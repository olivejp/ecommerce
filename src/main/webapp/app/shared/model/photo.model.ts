import { IModel } from 'app/shared/model/model.model';

export interface IPhoto {
  id?: number;
  name?: string;
  url?: string;
  model?: IModel;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public name?: string, public url?: string, public model?: IModel) {}
}
