package nl.nmc.dbxp.chemistry.resource

class ExampleController {

	def ChebiService
	def ChemspiderService
	def PubchemService
	def LipidmapsService
	def HmdbService
	def KeggService
	def BridgeDbService
	
    def index = { 
		render ("Help!")			
	}
	
	def chebi = {
		
		def args = [	'max': 3, 
						'synonyms':true, 
						'ontologychildren':true,
						'ontologyparents':true,
						'ontologychildreninpath':true 
					]
//		def args = ['max': 1]
		def chebies = ChebiService.findAllChebiByLabel('glucose' as String, args)
		
		render chebies
	}
	
	def chebies = {
		
		render "<h1>Seach by Label:</h1>"
		
		['glucose','flav*', '*oxide'].each { label ->
		//['glucose'].each { label ->
		
			def args = ['max': 5, 'details':false]
			def chebies = ChebiService.findAllChebiByLabel(label as String, args)
			render "<br /><h2>found ${chebies.size()}"
			if (args.max == chebies.size()) { render "+" }
			render " possible solutions for label ${label} with ${args}</h2><br />"
			render "<small>"
			chebies.each { chebiID, chebiMeta ->
				render "<h3>${chebiID} - ${chebiMeta.asciiName} (${chebiMeta.searchScore}/${chebiMeta.entityStar})</h3>"
				
				def s = ChebiService.findAllSynonymsChebiById(chebiID)
				if (s){
					render "<br /><i>Synonyms:</i><br />"
					s.each { synonym, synonymMeta ->
						render "<b>${synonym}</b> :: ${synonymMeta?.collect { it.value }.join(', ')}<br />"
					}
				}
				
				def oc = ChebiService.findAllOntologyChildrenByChebiId(chebiID)
				if (oc){
					render "<br /><i>Ontology Children:</i><br />"
					oc.each { ontologyChildChebiId, ontologyChildChebiMeta ->
						render "<b>${ontologyChildChebiId}</b> :: ${ontologyChildChebiMeta?.collect { it.value }.join(', ')}<br />"
					}
				}
									
				def op = ChebiService.findAllOntologyParentsByChebiId(chebiID)
				if (op){
					render "<br /><i>Ontology Parents:</i><br />"
					op.each { ontologyParentChebiId, ontologyParentChebiMeta ->
						render "<b>${ontologyParentChebiId}</b> :: ${ontologyParentChebiMeta?.collect { it.value }.join(', ')}<br />"
					}
				}
				
				def oip = ChebiService.findAllOntologyChildrenInPathByChebiId(chebiID)
				if (oip) {
					render "<br /><i>Ontologies in Path:</i><br />"
					oip.each { ontologyInPathChebiId, ontologyInPathChebiMeta ->
						render "<b>${ontologyInPathChebiId}</b> :: ${ontologyInPathChebiMeta.asciiName} (${ontologyInPathChebiMeta.searchScore}/${ontologyInPathChebiMeta.entityStar})<br />"
					}
				}

			}
			render "</small>"
		}	
	}
	
	def chemspider = {
		
		try {
			
			//def inchi = 'InChI=1S/C18H32O3/c1-2-3-4-5-7-10-13-16-17(21-16)14-11-8-6-9-12-15-18(19)20/h7,10,16-17H,2-6,8-9,11-15H2,1H3,(H,19,20)/b10-7-'
			def inchi = 'InChI=1S/C27H30O16/c1-8-17(32)20(35)22(37)26(40-8)39-7-15-18(33)21(36)23(38)27(42-15)43-25-19(34)16-13(31)5-10(28)6-14(16)41-24(25)9-2-3-11(29)12(30)4-9/h2-6,8,15,17-18,20-23,26-33,35-38H,7H2,1H3/t8-,15+,17-,18+,20+,21-,22+,23+,26+,27-/m0/s1'		
			
			def inchiKey 		= ChemspiderService.inchiToInchikey(inchi)
			def chemspiderId 	= ChemspiderService.inchiKeyToChemspiderId(inchiKey)
			def compoundName 	= ChemspiderService.chemspiderIdToCompoundName(chemspiderId)
			def chemspiderURL	= ChemspiderService.chemspiderUrlByChemspiderId(chemspiderId)
			
			render "I :: ${inchi} <br />"
			render "IK :: ${inchiKey} <br />"
			render "CID :: ${chemspiderId} <br />"
			render "CNAME :: ${compoundName} <br />"
			render "CURL :: <a target=\"_blank\" href=\"${chemspiderURL}\">${chemspiderURL}</a><br />"
		} catch (e) {
			log.error e
		}
		render "done!"
	}
	
	def pubchem = {
		
		render PubchemService.pubchemSearchPageByInchiKey('IKGXIBQEEMLURG-NVPNHPEKSA-N')
		
	}
	
	def lipidmaps = {
		
		//returns URL of LipidMaps page
		render LipidmapsService.lipidmapsUrlByLMID('LMSP03010003')
		
		//returns MOL data of Lipid
		render LipidmapsService.lipidmapsMOLDataByLMID('LMSP03010003')
	}
	
	def hmdb = {
		render HmdbService.hmdbUrlByHmdbId('HMDB00122')
	}
	
	def kegg = {
		render KeggService.keggUrlByKeggId('C00031')
	}
	
	def bridgedb = {
		render BridgeDbService.example()
	}
}

