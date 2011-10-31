package nl.nmc.dbxp.chemistry.resource

class HmdbService {

    static transactional = true

    def hmdbUrlByHmdbId(String hmdbId = ''){
    	return 'http://www.hmdb.ca/metabolites/' + hmdbId
    }

}
