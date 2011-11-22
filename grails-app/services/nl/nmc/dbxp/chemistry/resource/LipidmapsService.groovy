package nl.nmc.dbxp.chemistry.resource

import nl.nmc.dbxp.chemistry.ResourceService

/**
 * A Grails Service to provide easy access to the LipidMaps Database
 *
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class LipidmapsService extends ResourceService {

	static transactional = true

	/**
	 * Defines class used in BridgeDB to be able to look for identical resource keys
	 */
	String bridgeDbClass = 'Lipidmaps'

	/**
	 * @param	LMID	LipidMaps Identifier (e.g. LMSP03010003)
	 * @return	http://www.lipidmaps.org/data/LMSDRecord.php?LMID=LMSP03010003
	 */
	String lipidmapsUrlByLMID(String LMID = '') {
		return "http://www.lipidmaps.org/data/LMSDRecord.php?LMID=${LMID}" as String
	}

	/**
	 * @param	LMID	LipidMaps Identifier (e.g. LMSP03010003)
	 * @return	Mol file data
	 */
	String lipidmapsMOLDataByLMID(String LMID = ''){
		return ("http://www.lipidmaps.org/data/LMSDRecord.php?Mode=File&LMID=${LMID}".toURL().text) as String
	}
}
