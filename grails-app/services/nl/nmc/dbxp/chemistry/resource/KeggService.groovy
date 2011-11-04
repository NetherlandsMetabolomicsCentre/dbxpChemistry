package nl.nmc.dbxp.chemistry.resource

class KeggService {

    static transactional = true

	def keggUrlByKeggId(String keggId = ''){
		return 'http://www.kegg.jp/entry/' + keggId
	}
}
