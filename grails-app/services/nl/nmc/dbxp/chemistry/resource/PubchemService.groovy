package nl.nmc.dbxp.chemistry.resource

class PubchemService {

    static transactional = true

    def pubchemSearchPageByInchiKey(String inchiKey = '') {
		return 'http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=pccompound&term="' + inchiKey + '"[InChIKey]'
    }
	
	def pubchemUrlByPubchemId(String pubchemId = ''){
		return 'http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=' + pubchemId
	}
}
