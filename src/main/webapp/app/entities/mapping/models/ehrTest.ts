export interface IEhrTest {
  id: string;
  name?: string | null;
  ehrCode?: string | null;
  loincCode?: string | null;
}

export type NewIEhrTest = Omit<IEhrTest, 'id'> & { id: null };
