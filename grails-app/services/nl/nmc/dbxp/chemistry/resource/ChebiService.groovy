package nl.nmc.dbxp.chemistry.resource

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity
import uk.ac.ebi.chebi.webapps.chebiWS.model.SearchCategory
import uk.ac.ebi.chebi.webapps.chebiWS.model.StarsCategory
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType

class ChebiService {

	static transactional = true
	
	def findAllChebiByLabel (String label = '', HashMap args = ['max':1, 'synonyms':false, 'ontologychildren':false, 'ontologyparents':false, 'ontologychildreninpath':false]){
			
		def result = [:] // ChebiID:ChebiASCIIName
		
		try {
			def client = new ChebiWebServiceClient()
			client.getLiteEntity(label, SearchCategory.ALL, (args.max ?: 5) as int, StarsCategory.ALL)?.getListElement()?.each {				
				result[it.getChebiId()] = [:] 
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

	def findAllSynonymsChebiById (String chebiId = ''){

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

	def findAllOntologyChildrenByChebiId (String chebiId = ''){
		
		def result = [:]

		try {
			def client = new ChebiWebServiceClient()
			client.getOntologyChildren(chebiId).getListElement().each{
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['chebiName'] 			= it.getChebiName()
				result[it.getChebiId()]['chebiId'] 				= it.getChebiId()
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

	def findAllOntologyParentsByChebiId (String chebiId = ''){

		def result = [:]
		
		try {

			def client = new ChebiWebServiceClient()
			client.getOntologyParents(chebiId).getListElement()?.each{
				result[it.getChebiId()] = [:]
				result[it.getChebiId()]['chebiName'] 			= it.getChebiName()
				result[it.getChebiId()]['chebiId'] 				= it.getChebiId()
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

	def findAllOntologyChildrenInPathByChebiId (String chebiId = ''){

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
