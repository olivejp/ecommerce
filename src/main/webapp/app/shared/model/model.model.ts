import { IPhoto } from 'app/shared/model/photo.model';

export interface IModel {
  id?: number;
  name?: string;
  count?: number;
  photoUrls?: IPhoto[];
  articleId?: number;
}

export class Model implements IModel {
  constructor(public id?: number, public name?: string, public count?: number, public photoUrls?: IPhoto[], public articleId?: number) {}
}
