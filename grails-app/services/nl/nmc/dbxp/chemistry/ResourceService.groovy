package nl.nmc.dbxp.chemistry

import nl.nmc.dbxp.chemistry.db.mapper.BridgeDbService

class ResourceService {

    static transactional = true

	def BridgeDbService
	
    HashMap identicalResourceKeys(String lookupKey = '', String bridgeDbClass = null, String connector = null) {
		
		//a connector is required, when none is provided it will use the BridgeDb API (online)
		if(connector == null){
			connector = "idmapper-bridgerest:http://webservice.bridgedb.org/Human"
		}
		
		//optionally a BridgeDblClass can be provided to the method, by default it tries to resolve it from the child instance
		if (bridgeDbClass == null){
			bridgeDbClass = this.getBridgeDbClass()
		}
		
		return BridgeDbService.identicalResourceKeys(connector, bridgeDbClass, lookupKey)
    }
}
