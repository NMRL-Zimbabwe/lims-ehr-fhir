export interface Pagination {
  page: number;
  size: number;
  sort: string[];
}

export interface Search {
  text: string;
  query: string;
}

export interface SearchWithPagination extends Search, Pagination {}
