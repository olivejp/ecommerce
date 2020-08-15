import { Moment } from 'moment';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';

export interface ICommande {
  id?: number;
  date?: Moment;
  datePayment?: Moment;
  lignes?: ILigneCommande[];
  clientId?: number;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public date?: Moment,
    public datePayment?: Moment,
    public lignes?: ILigneCommande[],
    public clientId?: number
  ) {}
}
