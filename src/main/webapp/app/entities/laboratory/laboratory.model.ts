export interface ILaboratory {
  id: string;
  name?: string | null;
  code?: string | null;
  type?: string | null;
}

export type NewLaboratory = Omit<ILaboratory, 'id'> & { id: null };
