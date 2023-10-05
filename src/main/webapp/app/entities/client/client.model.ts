export interface IClient {
  id: string;
  name?: string | null;
  phone?: string | null;
  parentPath?: string | null;
  clientId?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
