export interface IAttributArticle {
  id?: number;
  value?: string;
  attributId?: number;
  articleId?: number;
}

export class AttributArticle implements IAttributArticle {
  constructor(public id?: number, public value?: string, public attributId?: number, public articleId?: number) {}
}
