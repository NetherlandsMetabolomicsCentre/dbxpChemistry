package nl.nmc.dbxp.chemistry.resource

import nl.nmc.dbxp.chemistry.ResourceService

/**
 * A Grails Service to provide easy access to the CAS number
 *
 * @since	November - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class CasService extends ResourceService {

	static transactional = true
	
	/**
	* Defines class used in BridgeDB to be able to look for identical resource keys
	*/
   String bridgeDbClass = 'CAS'
}