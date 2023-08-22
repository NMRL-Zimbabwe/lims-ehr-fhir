package zw.org.nmr.limsehr.service.subscriber.reference;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;

public class FhirClientUtility {

    private FhirClientUtility() {}

    public static IGenericClient getFhirClient() {
        String baseUrl = "http://localhost:8090/fhir";
        String username = "limsUsername";
        String password = "limsPassword";
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient fhirClient = fhirContext.newRestfulGenericClient(baseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(username, password);
        fhirClient.registerInterceptor(authInterceptor);
        return fhirClient;
    }
}
