package nl.nmc.dbxp.chemistry

import nl.nmc.dbxp.chemistry.db.mapper.BridgeDbService

class ResourceService {

    static transactional = true

	def BridgeDbService
	
    HashMap identicalResourceKeys(String lookupKey = '', String bridgeDbClass = null) {
		
		//optionally a BridgeDblClass can be provided to the method, by default it tries to resolve it from the child instance
		if (bridgeDbClass == null){
			bridgeDbClass = this.bridgeDbClass
		}
		
		return BridgeDbService.identicalResourceKeys("idmapper-text:file:///Users/miv/Documents/workspace-sts/dbxpChemistry/dbs/metabolites.txt", bridgeDbClass, lookupKey)
    }
}
