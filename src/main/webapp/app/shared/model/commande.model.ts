import { Moment } from 'moment';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { IClient } from 'app/shared/model/client.model';

export interface ICommande {
  id?: number;
  date?: Moment;
  datePayment?: Moment;
  lignes?: ILigneCommande[];
  client?: IClient;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public date?: Moment,
    public datePayment?: Moment,
    public lignes?: ILigneCommande[],
    public client?: IClient
  ) {}
}
