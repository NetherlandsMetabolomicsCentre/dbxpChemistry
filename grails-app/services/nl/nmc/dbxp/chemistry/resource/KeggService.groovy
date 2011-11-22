package nl.nmc.dbxp.chemistry.resource

import nl.nmc.dbxp.chemistry.ResourceService

/**
 * A Grails Service to provide easy access to the KEGG Database
 *
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class KeggService extends ResourceService {

	static transactional = true
	
	/**
	* Defines class used in BridgeDB to be able to look for identical resource keys
	*/
	String bridgeDbClass = 'Kegg'

	/**
	 * @param	keggId	e.g. C00031
	 * @return	http://www.kegg.jp/entry/C00031
	 */
	def keggUrlByKeggId(String keggId = ''){
		return 'http://www.kegg.jp/entry/' + keggId
	}
}
