import { ICommande } from 'app/shared/model/commande.model';

export interface IClient {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  photoUrl?: string;
  commandes?: ICommande[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public photoUrl?: string,
    public commandes?: ICommande[]
  ) {}
}
