package nl.nmc.dbxp.chemistry.resource

import nl.nmc.dbxp.chemistry.ResourceService;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity
import uk.ac.ebi.chebi.webapps.chebiWS.model.SearchCategory
import uk.ac.ebi.chebi.webapps.chebiWS.model.StarsCategory
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType

import nl.nmc.dbxp.chemistry.ResourceService

/**
 * A Grails Service to provide easy access to the Chebi Database
 * 
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class ChebiService extends ResourceService {

	static transactional = true

	/**
	 * Defines class used in BridgeDB to be able to look for identical resource keys
	 */
	String bridgeDbClass = 'Chebi'

	/**
	 * @param chebiId e.g 17634
	 * @return http://www.ebi.ac.uk/chebi/searchId.do?chebiId=17634
	 */
	String chebiUrlByChebiId(String chebiId = '') {
		return ('http://www.ebi.ac.uk/chebi/searchId.do?chebiId=' + chebiId.replace('CHEBI:','')) as String
	}

	/**
	 * Aggregate the responses of the following methods:<br />
	 * - findAllSynonymsChebiById()<br />
	 * - findAllOntologyChildrenByChebiId()<br />
	 * - findAllOntologyParentsByChebiId()<br />
	 * - findAllOntologyChildrenInPathByChebiId()<br /><br />
	 *
	 *	<code>
	 * 		ChebiService.findAllChebiByLabel('glucose', ['max':1, 'synonyms':true, 'ontologychildren':true, 'ontologyparents':true, 'ontologychildreninpath':true])<br />
	 * 		ChebiService.findAllChebiByLabel('flav*', ['max':5, 'synonyms':true, 'ontologychildren':false, 'ontologyparents':false, 'ontologychildreninpath':false])<br />
	 * 		ChebiService.findAllChebiByLabel('*oxide', ['max':5, 'synonyms':true, 'ontologychildren':false, 'ontologyparents':false, 'ontologychildreninpath':false])<br />
	 * 	</code>
	 *
	 * @param label Label used to look for in the database
	 * @param args A HashMap that specifies the max responses and the methods to include 
	 * @return a HashMap with the results
	 */
	HashMap findAllChebiByLabel (String label = '', HashMap args = ['max':1, 'synonyms':false, 'ontologychildren':false, 'ontologyparents':false, 'ontologychildreninpath':false]){

		def result = [:] // ChebiID:ChebiASCIIName

		try {
			def client = new ChebiWebServiceClient()
			client.getLiteEntity(label, SearchCategory.ALL, (args.max ?: 5) as int, StarsCategory.ALL)?.getListElement()?.each {
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['url'] 			= this.chebiUrlByChebiId(it.getChebiId())
				result[it.getChebiId()]['asciiName'] 	= it.getChebiAsciiName()
				result[it.getChebiId()]['searchScore'] 	= it.getSearchScore()
				result[it.getChebiId()]['entityStar'] 	= it.getEntityStar()

				//check if we have to load synonyms
				if (args.synonyms as boolean){
					result[it.getChebiId()]['synonyms'] = findAllSynonymsChebiById(it.getChebiId())
				}

				//check if we have to load ontologychildren
				if (args.ontologychildren as boolean){
					result[it.getChebiId()]['ontologychildren'] = findAllOntologyChildrenByChebiId(it.getChebiId())
				}

				//check if we have to load ontologyparents
				if (args.ontologyparents as boolean){
					result[it.getChebiId()]['ontologyparents'] = findAllOntologyParentsByChebiId(it.getChebiId())
				}

				//check if we have to load ontologychildreninpath
				if (args.ontologychildreninpath as boolean){
					result[it.getChebiId()]['ontologychildreninpath'] = findAllOntologyChildrenInPathByChebiId(it.getChebiId())
				}

			}
		} catch (e) {
			log.error e
		}

		return result
	}

	/**
	 * @param	chebiId
	 * @return	all synonyms as a HashMap	
	 */
	HashMap findAllSynonymsChebiById (String chebiId = ''){

		def result = [:] // ChebiID:ChebiASCIIName

		try {
			def client = new ChebiWebServiceClient()
			client.getCompleteEntity(chebiId).getSynonyms()?.each {
				result[it.getData()] = [:]
				result[it.getData()]['source'] 		= it.getSource()
				result[it.getData()]['type'] 		= it.getType()
				result[it.getData()]['comments'] 	= it.getComments()

			}
		} catch (e) {
			log.error e
		}

		return result
	}

	/**
	 * @param	chebiId
	 * @return	all ontology children as a HashMap
	 */
	HashMap findAllOntologyChildrenByChebiId (String chebiId = ''){

		def result = [:]

		try {
			def client = new ChebiWebServiceClient()
			client.getOntologyChildren(chebiId).getListElement().each{
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['chebiName'] 			= it.getChebiName()
				result[it.getChebiId()]['chebiId'] 				= it.getChebiId()
				result[it.getChebiId()]['url'] 					= this.chebiUrlByChebiId(it.getChebiId())
				result[it.getChebiId()]['type'] 				= it.getType()
				result[it.getChebiId()]['status'] 				= it.getStatus()
				result[it.getChebiId()]['cyclicRelationship'] 	= it.isCyclicRelationship()
				result[it.getChebiId()]['comments'] 			= it.getComments()
				result[it.getChebiId()]['ontologyElement'] 		= it.getOntologyElement()
			}
		} catch (e) {
			log.error e
		}

		return result
	}

	/**
	 * @param	chebiId
	 * @return	all ontology parents as a HashMap
	 */
	HashMap findAllOntologyParentsByChebiId (String chebiId = ''){

		def result = [:]

		try {

			def client = new ChebiWebServiceClient()
			client.getOntologyParents(chebiId).getListElement()?.each{
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['chebiName'] 			= it.getChebiName()
				result[it.getChebiId()]['chebiId'] 				= it.getChebiId()
				result[it.getChebiId()]['url'] 					= this.chebiUrlByChebiId(it.getChebiId())
				result[it.getChebiId()]['type'] 				= it.getType()
				result[it.getChebiId()]['status'] 				= it.getStatus()
				result[it.getChebiId()]['cyclicRelationship'] 	= it.isCyclicRelationship()
				result[it.getChebiId()]['comments'] 			= it.getComments()
				result[it.getChebiId()]['ontologyElement'] 		= it.getOntologyElement()
			}
		} catch (e) {
			log.error e
		}

		return result
	}

	/**
	 * @param	chebiId
	 * @return	all ontology children in path as a HashMap
	 */
	HashMap findAllOntologyChildrenInPathByChebiId (String chebiId = ''){

		def result = [:]

		try {

			// Create client
			def client = new ChebiWebServiceClient();
			client.getAllOntologyChildrenInPath(chebiId.replace('CHEBI:',''), RelationshipType.IS_A, false).getListElement().each {
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['asciiName'] 	= it.getChebiAsciiName()
				result[it.getChebiId()]['searchScore'] 	= it.getSearchScore()
				result[it.getChebiId()]['entityStar'] 	= it.getEntityStar()
			}
		} catch (e) {
			log.error e
		}

		return result
	}
}
