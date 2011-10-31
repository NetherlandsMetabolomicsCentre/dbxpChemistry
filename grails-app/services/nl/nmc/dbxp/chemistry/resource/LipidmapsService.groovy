package nl.nmc.dbxp.chemistry.resource

class LipidmapsService {

    static transactional = true
	
	def lipidmapsUrlByLMID(String LMID = '') {
		return "http://www.lipidmaps.org/data/LMSDRecord.php?LMID=${LMID}"
	}
	
	def lipidmapsMOLDataByLMID(String LMID = ''){
		return "http://www.lipidmaps.org/data/LMSDRecord.php?Mode=File&LMID=${LMID}".toURL().text
	}
}
