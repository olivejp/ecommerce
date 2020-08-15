import { IArticle } from 'app/shared/model/article.model';
import { IAttribut } from 'app/shared/model/attribut.model';

export interface ICategory {
  id?: number;
  name?: string;
  transiant?: boolean;
  categoryParentId?: number;
  articles?: IArticle[];
  attributs?: IAttribut[];
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public name?: string,
    public transiant?: boolean,
    public categoryParentId?: number,
    public articles?: IArticle[],
    public attributs?: IAttribut[]
  ) {
    this.transiant = this.transiant || false;
  }
}
