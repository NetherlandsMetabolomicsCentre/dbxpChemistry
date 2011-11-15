package nl.nmc.dbxp.chemistry.resource

/**
 * A Grails Service to provide easy access to the Pubchem Database
 *
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class PubchemService {

	static transactional = true

	/**
	 * Provides the url to the search page
	 *
	 * @param	inchiKey	InChIKey (e.g. IKGXIBQEEMLURG-NVPNHPEKSA-N)
	 * @return	http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=pccompound&term="IKGXIBQEEMLURG-NVPNHPEKSA-N"[InChIKey]
	 */
	String pubchemSearchPageByInchiKey(String inchiKey = '') {
		return ('http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=pccompound&term="' + inchiKey + '"[InChIKey]') as String
	}

	/**
	 * Provides the url to the summary page
	 * 
	 * @param	pubchemId	Pubchem identifier (e.g. 5280805)
	 * @return	http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=5280805
	 */
	String pubchemUrlByPubchemId(String pubchemId = ''){
		return ('http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=' + pubchemId) as String
	}
}
