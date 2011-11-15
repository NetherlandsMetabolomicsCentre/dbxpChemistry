package nl.nmc.dbxp.chemistry.resource

/**
 * A Grails Service to provide easy access to the Chemspider Database
 * 
 * @since	October - 2011
 * @author	Michael van Vliet
 * @email	m.s.vanvliet@lacdr.leidenuniv.nl
 */
class ChemspiderService {

	static transactional = true

	/**
	 * @param	inchi	InChI (e.g. InChI=1S/C27H30O16/c1-8-17(32)20(35)22(37)26(40-8)39-7-15-18(33)21(36)23(38)27(42-15)43-25-19(34)16-13(31)5-10(28)6-14(16)41-24(25)9-2-3-11(29)12(30)4-9/h2-6,8,15,17-18,20-23,26-33,35-38H,7H2,1H3/t8-,15+,17-,18+,20+,21-,22+,23+,26+,27-/m0/s1 )
	 * @return	an InChIKey. In this case IKGXIBQEEMLURG-NVPNHPEKSA-N
	 */
	String inchiToInchikey(String inchi = '') {

		def inchiKey = ''

		try {
			// try to fetch the InChIKey from Chemspider
			new XmlSlurper().parseText(new URL("http://www.chemspider.com/InChI.asmx/InChIToInChIKey?inchi=" + inchi.encodeAsURL()).openConnection().content.text).each { inchiKey = it as String }
		} catch(e){
			log.error e
		}

		return inchiKey as String
	}

	/**
	 * @param	inchiKey	InChIKey (e.g. IKGXIBQEEMLURG-NVPNHPEKSA-N)
	 * @return	the Chemspider indentifier. In this case 4444362
	 */
	String inchiKeyToChemspiderId(String inchiKey = '') {

		def chemspiderId = ''

		try { // try to fetch a ChemSpiderID from Chemspider
			new XmlSlurper().parseText(new URL("http://www.chemspider.com/InChI.asmx/InChIKeyToCSID?inchi_key=" + inchiKey.encodeAsURL()).openConnection().content.text).each { chemspiderId = it as String }
		} catch(e){
			log.error(e)
		}

		return chemspiderId as String
	}

	/**
	 * @param	chemspiderId	Chemspider Identifier (e.g. 4444362)
	 * @return	the name of the compound as listed in the Chemspider database. In this case: Rutin
	 */
	String chemspiderIdToCompoundName(String chemspiderId = ''){

		def strChemspiderCompoundName = ''

		try { // try to fetch title of Chemspider page to use as name of compound
			if(chemspiderId){
				strChemspiderCompoundName = (new URL('http://www.chemspider.com/Chemical-Structure.' + chemspiderId + '.html').openConnection().content.text.split('<title>')[1].split('</title>')[0]).replace('|', ':').split(':')[1]
			}
		} catch(e){
			log.error(e)
		}

		return strChemspiderCompoundName as String
	}

	/**
	 * @param	chemspiderId	Chemspider Identifier (e.g. 4444362)	
	 * @return	http://www.chemspider.com/Chemical-Structure.4444362.html
	 */
	String chemspiderUrlByChemspiderId(String chemspiderId = ''){
		return "http://www.chemspider.com/Chemical-Structure.${chemspiderId}.html" as String
	}
}
