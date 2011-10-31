package nl.nmc.dbxp.chemistry.resource

class ChemspiderService {

    static transactional = true
	
    def inchiToInchikey(String inchi = '') {
		
		def inchiKey = ''
		
		try { // try to fetch the InChIKey from Chemspider
			new XmlSlurper().parseText(new URL("http://www.chemspider.com/InChI.asmx/InChIToInChIKey?inchi=" + inchi.encodeAsURL()).openConnection().content.text).each { inchiKey = it as String }
		} catch(e){
			log.error e
		}
		
		return inchiKey
    }
	
	def inchiKeyToChemspiderId(String inchiKey = '') {
		
		def chemspiderId = ''
		
		try { // try to fetch a ChemSpiderID from Chemspider
			new XmlSlurper().parseText(new URL("http://www.chemspider.com/InChI.asmx/InChIKeyToCSID?inchi_key=" + inchiKey.encodeAsURL()).openConnection().content.text).each { chemspiderId = it as String }
		} catch(e){
			log.error(e)
		}
		
		return chemspiderId
	}
	
	def chemspiderIdToCompoundName(String chemspiderId = ''){
		
		def strChemspiderCompoundName = '' 
		
		try { // try to fetch title of Chemspider page to use as name of compound
			if(chemspiderId){
				strChemspiderCompoundName = (new URL('http://www.chemspider.com/Chemical-Structure.' + chemspiderId + '.html').openConnection().content.text.split('<title>')[1].split('</title>')[0]).replace('|', ':').split(':')[1]
			}
		} catch(e){
			log.error(e)
		}
		
		return strChemspiderCompoundName
	}
	
	def chemspiderUrlByChemspiderId(String chemspiderId = ''){
		return "http://www.chemspider.com/Chemical-Structure.${chemspiderId}.html"
	}
}
