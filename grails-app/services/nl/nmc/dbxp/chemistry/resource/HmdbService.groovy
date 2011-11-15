package nl.nmc.dbxp.chemistry.resource

/**
 * A Grails Service to provide easy access to the HMDB Database
 *
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class HmdbService {

	static transactional = true

	/**
	 * @param	hmdbId (e.g. HMDB00122)
	 * @return 	http://www.hmdb.ca/metabolites/HMDB00122 (MetaboCard)
	 */
	String hmdbUrlByHmdbId(String hmdbId = ''){
		return 'http://www.hmdb.ca/metabolites/' + hmdbId
	}
}
