import { IPhoto } from 'app/shared/model/photo.model';
import { IArticle } from 'app/shared/model/article.model';

export interface IModel {
  id?: number;
  name?: string;
  count?: number;
  photoUrls?: IPhoto[];
  article?: IArticle;
}

export class Model implements IModel {
  constructor(public id?: number, public name?: string, public count?: number, public photoUrls?: IPhoto[], public article?: IArticle) {}
}
