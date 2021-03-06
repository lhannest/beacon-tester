package bio.knowledge.validator;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bio.knowledge.client.ApiClient;
import bio.knowledge.client.ApiException;
import bio.knowledge.client.api.ConceptsApi;
import bio.knowledge.client.api.EvidenceApi;
import bio.knowledge.client.api.ExactmatchesApi;
import bio.knowledge.client.api.StatementsApi;
import bio.knowledge.client.api.SummaryApi;
import bio.knowledge.client.model.base.Concept;
import bio.knowledge.client.model.base.ConceptDetails;
import bio.knowledge.client.model.base.Evidence;
import bio.knowledge.client.model.base.IdentifiedEntity;
import bio.knowledge.client.model.base.Statement;
import bio.knowledge.validator.utilities.ThrowableBiFunction;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Validator {
	
	private final static Logger logger = Logger.getGlobal();

	private String keywords = "e";
	private final String semgroups = null;
	private final Integer pageNumber = 1;
	private final Integer pageSize = 50;
	
	// These fields are setup in validateBasicWorkflow()
	private String conceptId;
	private String statementId;
	private List<String> c;
	
	private final ConceptsApi conceptsApi;
	private final StatementsApi statementsApi;
	private final EvidenceApi evidenceApi;
	private final ExactmatchesApi exactmatchesApi;
	private final SummaryApi summaryApi;
	private final ApiClient apiClient;
	
	private void logError(Throwable thrown) {
		logger.log(Level.SEVERE, apiClient.getLastQuery(), thrown);
	}
	
	private void logError(String message) {
		logger.log(Level.SEVERE, apiClient.getLastQuery(), new RuntimeException(message));
	}
	
	/**
	 * Used as a control mechanism to exit the test.
	 */
	public class TestTerminationException extends RuntimeException { }
	
	private TestTerminationException terminate() {
		return new TestTerminationException();
	}

	public Validator(ApiClient apiClient) {
		conceptsApi = new ConceptsApi(apiClient);
		statementsApi = new StatementsApi(apiClient);
		evidenceApi = new EvidenceApi(apiClient);
		exactmatchesApi = new ExactmatchesApi(apiClient);
		summaryApi = new SummaryApi(apiClient);
		this.apiClient = apiClient;
	}

	public void validate() {
		logger.info("Testing basic workflow from searching a concept to getting a statement to getting evidence.");
		validateBasicWorkflow();
		
		try {
			logger.info("Testing concept paging");
			validatePaging((pageNumber, pageSize) -> { return (List) conceptsApi.getConcepts(keywords, null, pageNumber, pageSize); });
			logger.info("Testing statement paging");
			validatePaging((pageNumber, pageSize) -> { return (List) statementsApi.getStatements(c, pageNumber, pageSize, null, null); });
			logger.info("Testing evidence paging");
			validatePaging((pageNumber, pageSize) -> { return (List) evidenceApi.getEvidence(statementId, null, pageNumber, pageSize); });
		} catch (ApiException e) {
			logError(e);
			throw terminate();
		}
		
		try {
			logger.info("Testing semantic filter on concepts");
			validateConceptSemanticFilter();
		} catch (ApiException e) {
			logError(e);
		}
		try {
			logger.info("Testing semantic filter on statements");
			validateStatementSemanticFilter();
		} catch (ApiException e) {
			logError(e);
		}
	}
	
	private void validateBasicWorkflow() {
		int pageNumber = 1;
		int pageSize = 1;
		
		try {
			List<Concept> concepts = (List<Concept>) (List) conceptsApi.getConcepts(keywords, semgroups, pageNumber, pageSize);
			if (concepts.isEmpty()) {
				logError("No concepts returned");
				throw terminate();
			} else {
				conceptId = concepts.get(0).getId();
				c = Arrays.asList(new String[]{conceptId});
			}
		} catch (Exception e) {
			logError(e);
			throw terminate();
		}
		
		try {
			List<ConceptDetails> conceptDetails = (List<ConceptDetails>) (List) conceptsApi.getConceptDetails(conceptId);
			if (conceptDetails.isEmpty()) {
				logError("No concept details returned");
			}
		} catch (Exception e) {
			logError(e);
		}
		
		try {
			List<Statement> statements = (List<Statement>) (List) statementsApi.getStatements(c, pageNumber, pageSize, null, null);
			if (statements.isEmpty()) {
				logError("No statements returned");
				throw terminate();
			} else {
				statementId = statements.get(0).getId();
			}
		} catch (Exception e) {
			logError(e);
			throw terminate();
		}
		
		try {
			List<Evidence> evidences = (List<Evidence>) (List) evidenceApi.getEvidence(statementId, null, pageNumber, pageSize);
			if (evidences.isEmpty()) {
				logError("No evidence was returned");
			}
		} catch (Exception e) {
			logError(e);
		}
		
		try {
			// It is appropriate for this query to not return anything
			exactmatchesApi.getExactMatchesToConcept(conceptId);
		} catch (Exception e) {
			logError(e);
		}
	}
	
	private boolean validatePaging(ThrowableBiFunction<Integer, Integer, List<IdentifiedEntity>> f) throws ApiException {		
		List<IdentifiedEntity> entities = f.apply(1, 50);
		
		if (entities == null || entities.isEmpty()) { return false; }
		
		int size = (int) Math.floor(entities.size() / 2.0);
		
		List<IdentifiedEntity> half1 = f.apply(1, size);
		List<IdentifiedEntity> half2 = f.apply(2, size);
		
		for (int i = 0; i < size; i++) {
			if (!entities.get(i).getId().equals(half1.get(i).getId())) {
				return false;
			}
		}
		
		for (int i = 0; i < size; i++) {
			if (!entities.get(i + size).getId().equals(half2.get(i).getId())) {
				return false;
			}
		}
		
		return half1.size() + half2.size() == size * 2;
	}
	
	private void validateConceptSemanticFilter() throws ApiException {
		int pageNumber = 1;
		int pageSize = 50;
		for (SemanticGroup semanticGroup : SemanticGroup.values()) {
			List<Concept> concepts = (List<Concept>) (List) conceptsApi.getConcepts(keywords, semanticGroup.name(), pageNumber, pageSize);
			for (Concept concept : concepts) {
				boolean isValid = concept.getSemanticGroup().toLowerCase().equals(semanticGroup.name().toLowerCase());
				if (! isValid) {
					logError("Concept " + concept.getId() + " has semantic type " +
							concept.getSemanticGroup() + " when we were searching for concepts of type " + semanticGroup.name());
					break;
				}
			}
		}
	}
	
	private void validateStatementSemanticFilter() throws ApiException {
		int pageNumber = 1;
		int pageSize = 50;
		List<String> c = Arrays.asList(new String[]{conceptId});
		for (SemanticGroup semanticGroup : SemanticGroup.values()) {
			List<Statement> statements = (List<Statement>) (List) statementsApi.getStatements(c, pageNumber, pageSize, null, semanticGroup.name());
			for (Statement statement : statements) {
				String id = c.contains(statement.getObject().getId()) ? statement.getSubject().getId() : statement.getObject().getId();
				List<ConceptDetails> concepts = (List<ConceptDetails>) (List) conceptsApi.getConceptDetails(id);
				if (concepts.isEmpty()) {
					logError("No concept details found for concept ID " + id);
					break;
				} else {
					ConceptDetails concept = concepts.get(0);
					boolean isValid = concept.getSemanticGroup().toLowerCase().equals(semanticGroup.name().toLowerCase());
					if (! isValid) {
						logError(
								"Searched for statements containing concept with ID " + conceptId + " with semantic filter applied for " +
										semanticGroup.name() + "." +
								" Received statement with ID " + statement.getId() + " which has a concept with ID " + concept.getId() +
								" that is of semantic type " + concept.getSemanticGroup());
						break;
					}
				}
			}
		}
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeywords() {
		return this.keywords;
	}
}