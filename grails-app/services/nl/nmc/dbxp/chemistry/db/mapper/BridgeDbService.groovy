package nl.nmc.dbxp.chemistry.db.mapper

import org.bridgedb.*
import org.bridgedb.bio.*

class BridgeDbService {

    static transactional = true
	
	HashMap identicalResourceKeys(String connector, String bridgeDbClass, String lookupKey){
		
		Class.forName("org.bridgedb.file.IDMapperText")
		Class.forName("org.bridgedb.webservice.bridgerest.BridgeRest")
		
		HashMap keys = [:]
		
		BridgeDb.connect(connector).mapID(
			new Xref (lookupKey, DataSource.getByFullName(bridgeDbClass))).each { dest ->
				keys[dest.rep] = ['source':"${dest.ds}",'identifier':"${dest.id}"] 
		}
			
		return keys
	}
}
