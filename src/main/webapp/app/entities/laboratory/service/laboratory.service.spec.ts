import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IClient } from '../laboratory.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../client.test-samples';

import { ClientService } from './client.service';

const requireRestSample: IClient = {
  ...sampleWithRequiredData,
};

describe('Developer Service', () => {
  let service: ClientService;
  let httpMock: HttpTestingController;
  let expectedResult: IClient | IClient[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Developer', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const client = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(client).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Developer', () => {
      const client = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(client).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Developer', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Developer', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Developer', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDeveloperToCollectionIfMissing', () => {
      it('should add a Developer to an empty array', () => {
        const client: IClient = sampleWithRequiredData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], client);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(client);
      });

      it('should not add a Developer to an array that contains it', () => {
        const client: IClient = sampleWithRequiredData;
        const developerCollection: IClient[] = [
          {
            ...client,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, client);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Developer to an array that doesn't contain it", () => {
        const client: IClient = sampleWithRequiredData;
        const developerCollection: IClient[] = [sampleWithPartialData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, client);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(client);
      });

      it('should add only unique Developer to an array', () => {
        const developerArray: IClient[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const developerCollection: IClient[] = [sampleWithRequiredData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, ...developerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const client: IClient = sampleWithRequiredData;
        const developer2: IClient = sampleWithPartialData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], client, developer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(client);
        expect(expectedResult).toContain(developer2);
      });

      it('should accept null and undefined values', () => {
        const client: IClient = sampleWithRequiredData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], null, client, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(client);
      });

      it('should return initial array if no Developer is added', () => {
        const developerCollection: IClient[] = [sampleWithRequiredData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, undefined, null);
        expect(expectedResult).toEqual(developerCollection);
      });
    });

    describe('compareDeveloper', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDeveloper(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
