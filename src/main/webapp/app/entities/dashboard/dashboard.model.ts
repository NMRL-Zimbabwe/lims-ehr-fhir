export interface IDashboardSummary {
  patients: number;
  totalSamples: number;
  totalSamplesAcknowledged: number;
}

export type NewDashboardSummary = Omit<IDashboardSummary, 'id'> & { id: null };
