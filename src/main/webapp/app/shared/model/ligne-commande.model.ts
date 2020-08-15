export interface ILigneCommande {
  id?: number;
  total?: number;
  number?: number;
  modelId?: number;
  commandeId?: number;
}

export class LigneCommande implements ILigneCommande {
  constructor(public id?: number, public total?: number, public number?: number, public modelId?: number, public commandeId?: number) {}
}
