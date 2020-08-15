import { IAttributValue } from 'app/shared/model/attribut-value.model';
import { IArticle } from 'app/shared/model/article.model';

export interface IAttributArticle {
  id?: number;
  value?: string;
  attribut?: IAttributValue;
  article?: IArticle;
}

export class AttributArticle implements IAttributArticle {
  constructor(public id?: number, public value?: string, public attribut?: IAttributValue, public article?: IArticle) {}
}
