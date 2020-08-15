export interface IPhoto {
  id?: number;
  name?: string;
  url?: string;
  modelId?: number;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public name?: string, public url?: string, public modelId?: number) {}
}
