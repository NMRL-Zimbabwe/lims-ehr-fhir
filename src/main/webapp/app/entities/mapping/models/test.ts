export interface ITest {
  id: string;
  name?: string | null;
  testId?: string | null;
}

export type NewITest = Omit<ITest, 'id'> & { id: null };
