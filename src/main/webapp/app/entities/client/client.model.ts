export interface IClient {
  id: string;
  name?: string | null;
  phone?: string | null;
  activated?: number | null;
  clientId?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
