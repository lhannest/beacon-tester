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


package bio.knowledge.client.auth;

import bio.knowledge.client.Pair;

import java.util.Map;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-06-14T15:09:57.596-07:00")
public class OAuth implements Authentication {
  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public void applyToParams(List<Pair> queryParams, Map<String, String> headerParams) {
    if (accessToken != null) {
      headerParams.put("Authorization", "Bearer " + accessToken);
    }
  }
}
