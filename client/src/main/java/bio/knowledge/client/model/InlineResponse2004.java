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


package bio.knowledge.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

import bio.knowledge.client.model.base.Evidence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * InlineResponse2004
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-06-14T15:09:57.596-07:00")
public class InlineResponse2004 extends Evidence {
  @SerializedName("id")
  private String id = null;

  @SerializedName("label")
  private String label = null;

  @SerializedName("date")
  private String date = null;

  public InlineResponse2004 id(String id) {
    this.id = id;
    return this;
  }

   /**
   * CURIE-encoded identifier to an associated external resources (e.g. PMID of a pubmed citation) 
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "CURIE-encoded identifier to an associated external resources (e.g. PMID of a pubmed citation) ")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public InlineResponse2004 label(String label) {
    this.label = label;
    return this;
  }

   /**
   * canonical human readable and searchable label of the annotation (i.e. comment, matched sentence, etc.) 
   * @return label
  **/
  @ApiModelProperty(example = "null", value = "canonical human readable and searchable label of the annotation (i.e. comment, matched sentence, etc.) ")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public InlineResponse2004 date(String date) {
    this.date = date;
    return this;
  }

   /**
   * publication date of annotation (generally of format 'yyyy-mm-dd') 
   * @return date
  **/
  @ApiModelProperty(example = "null", value = "publication date of annotation (generally of format 'yyyy-mm-dd') ")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2004 inlineResponse2004 = (InlineResponse2004) o;
    return Objects.equals(this.id, inlineResponse2004.id) &&
        Objects.equals(this.label, inlineResponse2004.label) &&
        Objects.equals(this.date, inlineResponse2004.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, date);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2004 {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

