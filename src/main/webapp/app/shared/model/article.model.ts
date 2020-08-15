import { IModel } from 'app/shared/model/model.model';
import { IAttributArticle } from 'app/shared/model/attribut-article.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IArticle {
  id?: number;
  name?: string;
  description?: string;
  available?: boolean;
  price?: number;
  models?: IModel[];
  attributs?: IAttributArticle[];
  category?: ICategory;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public available?: boolean,
    public price?: number,
    public models?: IModel[],
    public attributs?: IAttributArticle[],
    public category?: ICategory
  ) {
    this.available = this.available || false;
  }
}
