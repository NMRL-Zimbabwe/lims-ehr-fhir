export interface ILaboratory {
  id: string;
  name?: string | null;
  code?: string | null;
  routingKey?: string | null;
  ehrCode?: string | null;
}

export type NewLaboratory = Omit<ILaboratory, 'id'> & { id: null };
