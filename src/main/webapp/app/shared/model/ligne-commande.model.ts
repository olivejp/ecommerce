import { IModel } from 'app/shared/model/model.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface ILigneCommande {
  id?: number;
  total?: number;
  number?: number;
  model?: IModel;
  commande?: ICommande;
}

export class LigneCommande implements ILigneCommande {
  constructor(public id?: number, public total?: number, public number?: number, public model?: IModel, public commande?: ICommande) {}
}
