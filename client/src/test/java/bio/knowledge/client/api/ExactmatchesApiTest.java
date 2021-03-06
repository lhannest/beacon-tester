/*
 * Translator Knowledge Beacon API
 * This is the Translator Knowledge Beacon web service application programming interface (API).  This OpenAPI (\"Swagger\") document may be used as the input specification into a tool like [Swagger-Codegen](https://github.com/swagger-api/swagger-codegen/blob/master/README.md) to generate client and server code stubs implementing the API, in any one of several supported computer languages and frameworks. In order to customize usage to your web site, you should change the 'host' directive below to your hostname. 
 *
 * OpenAPI spec version: 1.0.11
 * Contact: richard@starinformatics.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package bio.knowledge.client.api;

import bio.knowledge.client.ApiException;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ExactmatchesApi
 */
@Ignore
public class ExactmatchesApiTest {

    private final ExactmatchesApi api = new ExactmatchesApi();

    
    /**
     * 
     *
     * Retrieves a list of qualified identifiers of \&quot;exact match\&quot; concepts, [sensa SKOS](http://www.w3.org/2004/02/skos/core#exactMatch) associated with a specified (url-encoded) CURIE (without brackets) concept object identifier,  typically, of a concept selected from the list of concepts originally returned by a /concepts API call on a given KS.  
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getExactMatchesToConceptTest() throws ApiException {
        String conceptId = null;
        List<String> response = api.getExactMatchesToConcept(conceptId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * Given an input list of [CURIE](https://www.w3.org/TR/curie/) identifiers of known exactly matched concepts [*sensa*-SKOS](http://www.w3.org/2004/02/skos/core#exactMatch), retrieves the list of [CURIE](https://www.w3.org/TR/curie/) identifiers of **additional** concepts that are deemed by the given knowledge source to be exact matches to one or more of the input concepts.  If an empty set is returned, the it can be assumed that the given knowledge source does not know of any new equivalent concepts to add to the input set. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getExactMatchesToConceptListTest() throws ApiException {
        List<String> c = null;
        List<String> response = api.getExactMatchesToConceptList(c);

        // TODO: test validations
    }
    
}
