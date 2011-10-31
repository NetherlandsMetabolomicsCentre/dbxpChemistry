package nl.nmc.dbxp.chemistry.resource

import grails.converters.JSON

class ApiController {

	def ChebiService
	
	def apiCacheTTL = 60*60*24 //Cache of API by default is 1 day

	/*
	 * Chebi API
	 */
    def chebi = {
		
		cache validFor: params.ttl ?: apiCacheTTL //set cache TTL
		
		def result = [:] //prepare response
		
		try {
			ChebiService.findAllChebiByLabel(params.id as String, params as HashMap).each { chebiID, chebiMeta ->
				result[chebiID] = chebiMeta
			}
		} catch (e) {
			log.error(e)
		}
		
		render result as JSON
	}
}
